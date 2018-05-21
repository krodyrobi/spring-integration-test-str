package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<String> handleException(HttpServletRequest request, WebClientResponseException ex) {
        return new ResponseEntity<>(request.getRequestURL() + " " + ex.getResponseBodyAsString(), ex.getStatusCode());
    }
}
