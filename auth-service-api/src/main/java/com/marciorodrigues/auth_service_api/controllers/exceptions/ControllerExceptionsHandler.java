package com.marciorodrigues.auth_service_api.controllers.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import models.exceptions.StandardError;
import models.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;

@ControllerAdvice
public class ControllerExceptionsHandler {

    @ExceptionHandler( UsernameNotFoundException.class)
    ResponseEntity<StandardError> handleNotFoundException(final  UsernameNotFoundException ex,
                                              final HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                StandardError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ValidationException> handleMethodArgumentNotValidException( final MethodArgumentNotValidException ex,
                                                                         final HttpServletRequest request){
        var validationErrors = ValidationException.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation Exception")
                .message("Exception in validation attributes")
                .errors(new ArrayList<>())
                .path(request.getRequestURI())
                .build();

        for (FieldError fieldError: ex.getBindingResult().getFieldErrors()){
            validationErrors.addError(fieldError.getField(), fieldError.getDefaultMessage());

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrors);
    }



}
