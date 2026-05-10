package com.soumya.urlshortner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach((FieldError error) -> {
            errors.put(error.getField(),error.getDefaultMessage());
        });
        return errors;
    }
    @ExceptionHandler(UrlNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> handleUrlNotFoundException(UrlNotFoundException ex){
        Map<String,String> errors = new HashMap<>();
        errors.put("error",ex.getMessage());
        return errors;
    }
    @ExceptionHandler(ShortUrlExpiredException.class)
    @ResponseStatus(HttpStatus.GONE)
    public Map<String,String> handleExpiredUrlException(ShortUrlExpiredException ex){
        Map<String,String> errors = new HashMap<>();
        errors.put("error",ex.getMessage());
        return errors;
    }
    @ExceptionHandler(ShortCodeAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String,String> handleShortCodeConflict(ShortCodeAlreadyExistsException ex){
        Map<String,String> errors = new HashMap<>();
        errors.put("error",ex.getMessage());
        return errors;
    }
    @ExceptionHandler(RateLimitExceededException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public Map<String,String> handleRateLimitException(RateLimitExceededException ex){
        Map<String,String> errors = new HashMap<>();
        errors.put("error",ex.getMessage());
        return errors;
    }

}
