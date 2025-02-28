package com.fiuni.adoptamena.exception_handler.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.ObjectError;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(List<ObjectError> errors) {
        super(errors.stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(",")));
    }

}