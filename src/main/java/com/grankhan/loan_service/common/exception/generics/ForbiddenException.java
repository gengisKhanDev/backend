package com.grankhan.loan_service.common.exception.generics;

import com.grankhan.loan_service.common.exception.ApiException;
import org.springframework.http.HttpStatus;

//403 Forbidden: Falta de permisos.
public class ForbiddenException extends ApiException {
    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
