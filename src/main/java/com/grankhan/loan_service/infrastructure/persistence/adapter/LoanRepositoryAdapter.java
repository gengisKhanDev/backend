package com.grankhan.loan_service.infrastructure.persistence.adapter;

import com.grankhan.loan_service.domain.model.Loan;
import com.grankhan.loan_service.domain.port.LoanRepositoryPort;
import com.grankhan.loan_service.infrastructure.persistence.entity.LoanEntity;
import com.grankhan.loan_service.infrastructure.persistence.repository.LoanJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LoanRepositoryAdapter implements LoanRepositoryPort {

    private final LoanJpaRepository loanJpaRepository;

    public LoanRepositoryAdapter(LoanJpaRepository loanJpaRepository) {
        this.loanJpaRepository = loanJpaRepository;
    }

    @Override
    public Loan save(Loan loan) {
        LoanEntity entity = toEntity(loan);
        LoanEntity saved = loanJpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Loan> findById(Long id) {
        return loanJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Loan> findByUserId(Long userId) {
        return loanJpaRepository.findByUserId(userId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Loan> findAll() {
        return loanJpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private LoanEntity toEntity(Loan loan) {
        LoanEntity e = new LoanEntity();
        e.setId(loan.getId());
        e.setUserId(loan.getUserId());
        e.setAmount(loan.getAmount());
        e.setTermInMonths(loan.getTermInMonths());
        e.setStatus(loan.getStatus());
        e.setInterestRate(loan.getInterestRate());
        e.setCreatedAt(loan.getCreatedAt());
        e.setUpdatedAt(loan.getUpdatedAt());
        e.setReviewedByUserId(loan.getReviewedByUserId());
        e.setReviewedAt(loan.getReviewedAt());
        return e;
    }

    private Loan toDomain(LoanEntity e) {
        return new Loan(
                e.getId(),
                e.getUserId(),
                e.getAmount(),
                e.getTermInMonths(),
                e.getStatus(),
                e.getInterestRate(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getReviewedByUserId(),
                e.getReviewedAt()
        );
    }
}
