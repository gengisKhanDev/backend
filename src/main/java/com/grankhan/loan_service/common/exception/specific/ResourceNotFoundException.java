package com.grankhan.loan_service.common.exception.specific;

import com.grankhan.loan_service.common.exception.generics.NotFoundException;

public class ResourceNotFoundException extends NotFoundException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
