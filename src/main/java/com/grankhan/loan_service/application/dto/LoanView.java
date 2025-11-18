package com.grankhan.loan_service.application.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record LoanView(
        Long id,
        Long userId,
        BigDecimal amount,
        Integer termInMonths,
        String status,
        BigDecimal interestRate,
        Instant createdAt,
        Instant updatedAt
) {}
