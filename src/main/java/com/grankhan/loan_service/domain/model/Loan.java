package com.grankhan.loan_service.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class Loan {

    private Long id;
    private Long userId;
    private BigDecimal amount;
    private Integer termInMonths;
    private LoanStatus status;
    private BigDecimal interestRate;
    private Instant createdAt;
    private Instant updatedAt;

    // NUEVOS CAMPOS
    private Long reviewedByUserId;
    private Instant reviewedAt;

    public Loan(Long id,
                Long userId,
                BigDecimal amount,
                Integer termInMonths,
                LoanStatus status,
                BigDecimal interestRate,
                Instant createdAt,
                Instant updatedAt,
                Long reviewedByUserId,
                Instant reviewedAt) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.termInMonths = termInMonths;
        this.status = status;
        this.interestRate = interestRate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.reviewedByUserId = reviewedByUserId;
        this.reviewedAt = reviewedAt;
    }

    public static Loan newPending(Long userId, BigDecimal amount, Integer termInMonths) {
        Instant now = Instant.now();
        BigDecimal defaultInterest = BigDecimal.valueOf(0.15); // 15% demo
        return new Loan(
                null,
                userId,
                amount,
                termInMonths,
                LoanStatus.PENDING,
                defaultInterest,
                now,
                now,
                null,
                null
        );
    }

    public void approve(Long adminUserId) {
        if (status != LoanStatus.PENDING) {
            throw new IllegalStateException("Solo se puede aprobar un préstamo pendiente");
        }
        Instant now = Instant.now();
        this.status = LoanStatus.APPROVED;
        this.reviewedByUserId = adminUserId;
        this.reviewedAt = now;
        this.updatedAt = now;
    }

    public void reject(Long adminUserId) {
        if (status != LoanStatus.PENDING) {
            throw new IllegalStateException("Solo se puede rechazar un préstamo pendiente");
        }
        Instant now = Instant.now();
        this.status = LoanStatus.REJECTED;
        this.reviewedByUserId = adminUserId;
        this.reviewedAt = now;
        this.updatedAt = now;
    }
}
