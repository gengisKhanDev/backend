package com.grankhan.loan_service.application.usecase;

import com.grankhan.loan_service.application.dto.ApproveLoanCommand;
import com.grankhan.loan_service.application.dto.LoanView;
import com.grankhan.loan_service.domain.model.Loan;
import com.grankhan.loan_service.domain.model.User;
import com.grankhan.loan_service.domain.port.LoanRepositoryPort;
import com.grankhan.loan_service.domain.port.UserRepositoryPort;
import com.grankhan.loan_service.infrastructure.mapper.LoanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApproveLoanUseCase {

    private final LoanRepositoryPort loanRepository;
    private final UserRepositoryPort userRepository;
    private final LoanMapper loanMapper;

    public ApproveLoanUseCase(LoanRepositoryPort loanRepository,
                              UserRepositoryPort userRepository,
                              LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.loanMapper = loanMapper;
    }

    @Transactional
    public LoanView execute(ApproveLoanCommand cmd) {
        User admin = userRepository.findById(cmd.adminUserId())
                .orElseThrow(() -> new IllegalArgumentException("Admin no encontrado"));
        if (!admin.isAdmin()) {
            throw new SecurityException("Solo un admin puede aprobar/rechazar préstamos");
        }

        Loan loan = loanRepository.findById(cmd.loanId())
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));

        User borrower = userRepository.findById(loan.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario del préstamo no encontrado"));

        if (cmd.approved()) {
            loan.approve(admin.getId());
        } else {
            loan.reject(admin.getId());
        }

        Loan saved = loanRepository.save(loan);
        return loanMapper.toView(saved, borrower, admin);
    }
}
