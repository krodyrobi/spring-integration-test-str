package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;



@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<String> handleException(ServerHttpRequest request, WebClientResponseException ex) {
        return new ResponseEntity<>(request.getURI() + " " + ex.getResponseBodyAsString(), ex.getStatusCode());
    }
}
