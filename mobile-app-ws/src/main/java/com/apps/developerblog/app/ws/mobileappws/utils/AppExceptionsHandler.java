package com.apps.developerblog.app.ws.mobileappws.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 *  This class listens for exceptions that take place inside the application. To do this, the class
 *  must be annotated with @ControllerAdvice.
 */
@ControllerAdvice
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {

    /**
     * The method that actually handles the exception must be annotated with @ExceptionHandler. The value
     * for the annotation here is Exception.class meaning it listens for all general exceptions. If you want
     * to listen for specific exception like NullPointerExceptions, then you should enter NullPointerException.class.
     * Same data type for the method argument.
     */
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
        String message = (ex.getLocalizedMessage() == null) ? ex.toString() : ex.getLocalizedMessage();
        ErrorMessage errorMessage = ErrorMessage.create()
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Global exception for custom exception UserServiceException
     */
    @ExceptionHandler(value = {UserServiceException.class})
    public ResponseEntity<Object> handleCustomException(UserServiceException ex, WebRequest request) {
        String message = (ex.getLocalizedMessage() == null) ? ex.toString() : ex.getLocalizedMessage();
        ErrorMessage errorMessage = ErrorMessage.create()
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * To handle many specific exceptions into one method, just enter the specific exceptions in @ExceptionHandler
     * value with curly braces, then provide general Exception type as method argument.
     */
//    @ExceptionHandler(value = {UserServiceException.class, NullPointerException.class})
//    public ResponseEntity<Object> handleSeveralExceptions(Exception ex, WebRequest request) {
//        String message = (ex.getLocalizedMessage() == null) ? ex.toString() : ex.getLocalizedMessage();
//        ErrorMessage errorMessage = ErrorMessage.create()
//                .message(message)
//                .timestamp(LocalDateTime.now())
//                .build();
//
//        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
