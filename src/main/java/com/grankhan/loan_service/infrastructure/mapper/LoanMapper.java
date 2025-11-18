package com.grankhan.loan_service.infrastructure.mapper;

import com.grankhan.loan_service.application.dto.LoanView;
import com.grankhan.loan_service.domain.model.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {

    public LoanView toView(Loan loan) {
        return new LoanView(
                loan.getId(),
                loan.getUserId(),
                loan.getAmount(),
                loan.getTermInMonths(),
                loan.getStatus().name(),
                loan.getInterestRate(),
                loan.getCreatedAt(),
                loan.getUpdatedAt()
        );
    }
}
