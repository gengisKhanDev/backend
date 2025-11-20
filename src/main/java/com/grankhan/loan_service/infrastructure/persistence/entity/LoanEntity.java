package com.grankhan.loan_service.infrastructure.persistence.entity;

import com.grankhan.loan_service.domain.model.LoanStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "loans")
@Getter
@Setter
public class LoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private BigDecimal amount;

    private Integer termInMonths;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    private BigDecimal interestRate;

    private Instant createdAt;

    private Instant updatedAt;

    private Long reviewedByUserId;

    private Instant reviewedAt;
}
