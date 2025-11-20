package com.grankhan.loan_service.application.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record LoanView(
        Long id,
        Long userId,
        String userName,
        String userEmail,
        BigDecimal amount,
        Integer term,         // meses
        String status,        // "pending", "approved", "rejected"
        Instant requestDate,  // createdAt
        Instant reviewDate,   // reviewedAt (puede ser null)
        String reviewedBy     // nombre del admin (puede ser null)
) {}
