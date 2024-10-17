package com.consti.security.exceptions;

import io.jsonwebtoken.JwtException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleNotFoundException(ResourceNotFoundException ex,
                                                               WebRequest webRequest) {
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(), webRequest.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse> handleJWTException(JwtException ex,
                                                     WebRequest webRequest) {
        ApiResponse apiResponse = new ApiResponse("Credenciales no validas",webRequest.getDescription(false));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> canNotAlterException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                        WebRequest webRequest) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ApiResponse apiResponse = new ApiResponse(errors.toString(), webRequest.getDescription(false));
        return new ResponseEntity<Object>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}

