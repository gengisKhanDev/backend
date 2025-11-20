package com.grankhan.loan_service.application.usecase;

import com.grankhan.loan_service.application.dto.LoanView;
import com.grankhan.loan_service.domain.model.Loan;
import com.grankhan.loan_service.domain.model.User;
import com.grankhan.loan_service.domain.port.LoanRepositoryPort;
import com.grankhan.loan_service.domain.port.UserRepositoryPort;
import com.grankhan.loan_service.infrastructure.mapper.LoanMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllLoansUseCase {

    private final LoanRepositoryPort loanRepository;
    private final UserRepositoryPort userRepository;
    private final LoanMapper loanMapper;

    public GetAllLoansUseCase(LoanRepositoryPort loanRepository,
                              UserRepositoryPort userRepository,
                              LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.loanMapper = loanMapper;
    }

    public List<LoanView> execute() {
        List<Loan> loans = loanRepository.findAll();

        return loans.stream()
                .map(loan -> {
                    User borrower = userRepository.findById(loan.getUserId())
                            .orElseThrow(() -> new IllegalArgumentException("Usuario del préstamo no encontrado"));

                    User reviewer = null;
                    if (loan.getReviewedByUserId() != null) {
                        reviewer = userRepository.findById(loan.getReviewedByUserId()).orElse(null);
                    }

                    return loanMapper.toView(loan, borrower, reviewer);
                })
                .toList();
    }
}
