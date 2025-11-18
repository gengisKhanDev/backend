package com.grankhan.loan_service.application.dto;

import java.math.BigDecimal;

public record RequestLoanCommand(
        Long userId,
        BigDecimal amount,
        Integer termInMonths
) {}
