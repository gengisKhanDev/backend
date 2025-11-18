package com.grankhan.loan_service.common.exception.specific;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
