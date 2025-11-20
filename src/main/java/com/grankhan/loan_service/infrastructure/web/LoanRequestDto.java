package com.grankhan.loan_service.infrastructure.web;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record LoanRequestDto(
        @NotNull @Min(1) BigDecimal amount,
        @NotNull @Min(1) Integer term
) {}
