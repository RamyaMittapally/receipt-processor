package com.example.receipt_processor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)  // Return 404 status
    @ExceptionHandler(ReceiptNotFoundException.class)
    public Map<String, Object> handleReceiptNotFound(ReceiptNotFoundException ex) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("error", ex.getMessage());
        error.put("status", 404);
        return error;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // Return 500 status
    @ExceptionHandler(Exception.class)
    public Map<String, Object> handleGeneric(Exception ex) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("error", "Internal Server Error");
        error.put("details", ex.getMessage());
        error.put("status", 500);
        return error;
    }
}
