package com.grankhan.loan_service.application.service;

import com.grankhan.loan_service.domain.model.Loan;
import com.grankhan.loan_service.domain.model.User;
import com.grankhan.loan_service.domain.port.LoanDomainService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LoanDomainServiceImpl implements LoanDomainService {

    @Override
    public Loan validateAndCreate(User user, BigDecimal amount, Integer termInMonths) {

        if (user == null) {
            throw new IllegalArgumentException("El usuario es obligatorio");
        }

        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }

        if (termInMonths == null || termInMonths <= 0) {
            throw new IllegalArgumentException("El plazo debe ser mayor que cero");
        }

        // Aquí podrías agregar más reglas (monto máximo, etc.) si quieres.
        // Pero para la prueba, con estas validaciones y la factory de Loan es suficiente.

        return Loan.newPending(user.getId(), amount, termInMonths);
    }
}
