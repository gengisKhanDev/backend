package com.grankhan.loan_service.application.usecase;

import com.grankhan.loan_service.application.dto.LoanView;
import com.grankhan.loan_service.domain.model.Loan;
import com.grankhan.loan_service.domain.port.LoanRepositoryPort;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetLoansForUserUseCase {

    private final LoanRepositoryPort loanRepository;

    public GetLoansForUserUseCase(LoanRepositoryPort loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Cacheable(cacheNames = "loansByUser", key = "#userId")
    public List<LoanView> execute(Long userId) {
        List<Loan> loans = loanRepository.findByUserId(userId);
        return loans.stream()
                .map(l -> new LoanView(
                        l.getId(),
                        l.getUserId(),
                        l.getAmount(),
                        l.getTermInMonths(),
                        l.getStatus().name(),
                        l.getInterestRate(),
                        l.getCreatedAt(),
                        l.getUpdatedAt()
                ))
                .toList();
    }
}
