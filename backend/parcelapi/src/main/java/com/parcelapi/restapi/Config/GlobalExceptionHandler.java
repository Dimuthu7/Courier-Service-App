package com.parcelapi.restapi.Config;

import com.parcelapi.restapi.Utility.ApiResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(new ApiResponse<String>(false, "", ex.getBindingResult().getAllErrors().get(0).getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<ApiResponse<String>> handleBadCredential(BadCredentialsException ex) {
        return new ResponseEntity<>(new ApiResponse<String>(false, "", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<ApiResponse<String>> handleSqlException(DataIntegrityViolationException ex) { 
        return new ResponseEntity<>(new ApiResponse<String>(false, "", "DataIntegrityViolation : " + ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ApiResponse<String>> handleMethodNotAllowedAException(HttpRequestMethodNotSupportedException ex) { 
        return new ResponseEntity<>(new ApiResponse<String>(false, "", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ApiResponse<String>> handleAccessDeniedAException(AccessDeniedException ex) { 
        return new ResponseEntity<>(new ApiResponse<String>(false, "", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
