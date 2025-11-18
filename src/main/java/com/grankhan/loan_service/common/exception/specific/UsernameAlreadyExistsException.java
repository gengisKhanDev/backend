package com.grankhan.loan_service.common.exception.specific;

import com.grankhan.loan_service.common.exception.generics.ConflictException;

public class UsernameAlreadyExistsException extends ConflictException {
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
