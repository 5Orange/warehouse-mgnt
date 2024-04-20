package com.mgnt.warehouse.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerAdviceHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public final ResponseEntity<?> badCredentialsException(ConstraintViolationException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getLocalizedMessage());
    }

}
