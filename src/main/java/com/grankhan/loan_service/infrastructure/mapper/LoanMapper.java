package com.grankhan.loan_service.infrastructure.mapper;

import com.grankhan.loan_service.application.dto.LoanView;
import com.grankhan.loan_service.domain.model.Loan;
import com.grankhan.loan_service.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {

    public LoanView toView(Loan loan, User borrower, User reviewerOrNull) {
        String status = loan.getStatus() != null
                ? loan.getStatus().name().toLowerCase()
                : null;

        return new LoanView(
                loan.getId(),
                borrower.getId(),
                borrower.getName(),
                borrower.getEmail(),
                loan.getAmount(),
                loan.getTermInMonths(),
                status,
                loan.getCreatedAt(),
                loan.getReviewedAt(),
                reviewerOrNull != null ? reviewerOrNull.getName() : null
        );
    }
}
