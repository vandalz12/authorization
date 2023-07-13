package com.dans.authorization.common;

import com.dans.authorization.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = { ExternalSystemException.class })
    public ResponseEntity<ErrorMessage> externalSystemException(ExternalSystemException exception) {
        return ResponseEntity.status(exception.getStatusCode()).body(
                ErrorMessage
                        .builder()
                        .message(exception.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = { BadCredentialsException.class })
    public ResponseEntity<ErrorMessage> badCredentialsException(BadCredentialsException exception) {
        return ResponseEntity.badRequest().body(
                ErrorMessage
                        .builder()
                        .message(exception.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = { KeyGeneratorException.class })
    public ResponseEntity<ErrorMessage> keyGeneratorException(KeyGeneratorException exception) {
        return ResponseEntity.internalServerError().body(
                ErrorMessage
                        .builder()
                        .message(exception.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = { UserAlreadyExistsException.class })
    public ResponseEntity<ErrorMessage> userAlreadyExistsException(UserAlreadyExistsException exception) {
        return ResponseEntity.badRequest().body(
                ErrorMessage
                        .builder()
                        .message(exception.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = { UserNotFoundException.class })
    public ResponseEntity<ErrorMessage> userNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.internalServerError().body(
                ErrorMessage
                        .builder()
                        .message(exception.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<ErrorMessage> generalException(Exception exception) {
        return ResponseEntity.internalServerError().body(
                ErrorMessage
                        .builder()
                        .message(exception.getMessage())
                        .build()
        );
    }

}
