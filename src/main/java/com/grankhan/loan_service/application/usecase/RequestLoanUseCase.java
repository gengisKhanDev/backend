package com.grankhan.loan_service.application.usecase;

import com.grankhan.loan_service.application.dto.LoanView;
import com.grankhan.loan_service.application.dto.RequestLoanCommand;
import com.grankhan.loan_service.domain.model.Loan;
import com.grankhan.loan_service.domain.model.User;
import com.grankhan.loan_service.domain.port.LoanDomainService;
import com.grankhan.loan_service.domain.port.LoanRepositoryPort;
import com.grankhan.loan_service.domain.port.UserRepositoryPort;
import com.grankhan.loan_service.infrastructure.mapper.LoanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RequestLoanUseCase {

    private final LoanRepositoryPort loanRepository;
    private final UserRepositoryPort userRepository;
    private final LoanDomainService loanDomainService;
    private final LoanMapper loanMapper;

    public RequestLoanUseCase(LoanRepositoryPort loanRepository,
                              UserRepositoryPort userRepository,
                              LoanDomainService loanDomainService,
                              LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.loanDomainService = loanDomainService;
        this.loanMapper = loanMapper;
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
        return loanMapper.toView(saved, user, null);
    }
}
