package com.grankhan.loan_service.common.exception.generics;

import com.grankhan.loan_service.common.exception.ApiException;
import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends ApiException {

    public InternalServerErrorException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        // 👇 cambiamos el orden de los parámetros
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, cause);
    }
}
