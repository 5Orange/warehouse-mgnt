package com.mgnt.warehouse.exception;

import com.mgnt.warehouse.modal.exception.DuplicateException;
import com.mgnt.warehouse.modal.exception.NotFoundException;
import com.mgnt.warehouse.modal.response.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ControllerAdviceHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public final ResponseEntity<?> badCredentialsException(ConstraintViolationException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(
                ErrorResponse.builder().message(ex.getLocalizedMessage()).status(HttpStatus.UNAUTHORIZED).build());
    }

    @ExceptionHandler({DuplicateException.class, NotFoundException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public final ResponseEntity<?> badRequestException(Exception ex, WebRequest request) {
        return ResponseEntity.badRequest()
                .body(ErrorResponse.builder().message(ex.getLocalizedMessage()).status(HttpStatus.BAD_REQUEST).build());
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public final ResponseEntity<?> handlerMethodValidationException(HandlerMethodValidationException ex,
                                                                    WebRequest request) {
        var errorMessage = ex.getAllValidationResults()
                .stream()
                .flatMap(x -> x.getResolvableErrors()
                        .stream()
                        .map(MessageSourceResolvable::getDefaultMessage))
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                        .message(errorMessage)
                        .status(HttpStatus.BAD_REQUEST).build());
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public final ResponseEntity<ErrorResponse> applicationException(Exception ex, WebRequest request) {
        log.error("error", ex);
        return ResponseEntity.badRequest().body(ErrorResponse.builder().message(ex.getLocalizedMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

}
