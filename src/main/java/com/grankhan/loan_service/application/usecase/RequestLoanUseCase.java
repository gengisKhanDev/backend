package com.grankhan.loan_service.application.usecase;

import com.grankhan.loan_service.application.dto.LoanView;
import com.grankhan.loan_service.application.dto.RequestLoanCommand;
import com.grankhan.loan_service.domain.model.Loan;
import com.grankhan.loan_service.domain.model.User;
import com.grankhan.loan_service.domain.port.LoanDomainService;
import com.grankhan.loan_service.domain.port.LoanRepositoryPort;
import com.grankhan.loan_service.domain.port.UserRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RequestLoanUseCase {

    private final LoanRepositoryPort loanRepository;
    private final UserRepositoryPort userRepository;
    private final LoanDomainService loanDomainService;

    public RequestLoanUseCase(LoanRepositoryPort loanRepository,
                              UserRepositoryPort userRepository,
                              LoanDomainService loanDomainService) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.loanDomainService = loanDomainService;
    }

    @Transactional
    public LoanView execute(RequestLoanCommand cmd) {
        User user = userRepository.findById(cmd.userId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Loan loan = loanDomainService.validateAndCreate(
                user,
                cmd.amount(),
                cmd.termInMonths()
        );

        Loan saved = loanRepository.save(loan);
        return toView(saved);
    }

    private LoanView toView(Loan loan) {
        return new LoanView(
                loan.getId(),
                loan.getUserId(),
                loan.getAmount(),
                loan.getTermInMonths(),
                loan.getStatus().name(),
                loan.getInterestRate(),
                loan.getCreatedAt(),
                loan.getUpdatedAt()
        );
    }
}
