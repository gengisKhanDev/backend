package com.grankhan.loan_service.domain.port;

import com.grankhan.loan_service.domain.model.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanRepositoryPort {

    Loan save(Loan loan);

    Optional<Loan> findById(Long id);

    List<Loan> findByUserId(Long userId);

    List<Loan> findAll();
}
