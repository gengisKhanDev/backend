package com.grankhan.loan_service.common.exception.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.Instant;
import java.util.Map;
/*
* Campos:
timestamp: Fecha y hora en que ocurrió el error.
status: Código de estado HTTP.
error: Mensaje corto del error (por ejemplo, "Bad Request").
message: Detalle del error.
path: Ruta del endpoint que causó el error.
validationErrors: Mapa de errores de validación por campo (opcional, solo para errores de validación).
* */

public record ErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> validationErrors
) {
    public static ErrorResponse of(int status, String error, String message, String path) {
        return new ErrorResponse(
                Instant.now(),
                status,
                error,
                message,
                path,
                null
        );
    }

    public static ErrorResponse withValidation(int status, String error, String message,
                                               String path, Map<String, String> validationErrors) {
        return new ErrorResponse(
                Instant.now(),
                status,
                error,
                message,
                path,
                validationErrors
        );
    }
}

