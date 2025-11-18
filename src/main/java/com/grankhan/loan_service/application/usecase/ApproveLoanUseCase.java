package com.grankhan.loan_service.application.usecase;

import com.grankhan.loan_service.application.dto.ApproveLoanCommand;
import com.grankhan.loan_service.application.dto.LoanView;
import com.grankhan.loan_service.domain.model.Loan;
import com.grankhan.loan_service.domain.model.User;
import com.grankhan.loan_service.domain.port.LoanRepositoryPort;
import com.grankhan.loan_service.domain.port.UserRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApproveLoanUseCase {

    private final LoanRepositoryPort loanRepository;
    private final UserRepositoryPort userRepository;

    public ApproveLoanUseCase(LoanRepositoryPort loanRepository,
                              UserRepositoryPort userRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
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

        if (cmd.approved()) {
            loan.approve();
        } else {
            loan.reject();
        }

        Loan saved = loanRepository.save(loan);
        return new LoanView(
                saved.getId(),
                saved.getUserId(),
                saved.getAmount(),
                saved.getTermInMonths(),
                saved.getStatus().name(),
                saved.getInterestRate(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );
    }
}
