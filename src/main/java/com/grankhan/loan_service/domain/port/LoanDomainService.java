package com.grankhan.loan_service.domain.port;

import com.grankhan.loan_service.domain.model.Loan;
import com.grankhan.loan_service.domain.model.User;

import java.math.BigDecimal;

public interface LoanDomainService {

    Loan validateAndCreate(User user,
                           BigDecimal amount,
                           Integer termInMonths);
}
