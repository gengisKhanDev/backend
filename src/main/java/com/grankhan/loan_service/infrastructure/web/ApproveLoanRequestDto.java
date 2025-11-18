package com.grankhan.loan_service.infrastructure.web;

import jakarta.validation.constraints.NotNull;

public record ApproveLoanRequestDto(
        @NotNull Boolean approved
) {}
