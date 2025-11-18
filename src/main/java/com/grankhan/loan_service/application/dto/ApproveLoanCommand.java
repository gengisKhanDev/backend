package com.grankhan.loan_service.application.dto;

public record ApproveLoanCommand(
        Long loanId,
        Long adminUserId,
        boolean approved
) {}
