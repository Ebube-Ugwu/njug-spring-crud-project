package com.example.njug_spring_crud_project.controllers;

import com.example.njug_spring_crud_project.exceptions.DuplicateEmailException;
import com.example.njug_spring_crud_project.exceptions.EmployeeNotFoundException;
import com.example.njug_spring_crud_project.exceptions.InternalServerError;
import jakarta.persistence.ElementCollection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateEmailException() {
        return ResponseEntity.badRequest().body(
                Map.of("email", "Email is already registered")
        );
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Void> handleEmployeeNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleIllegalStateException(IllegalStateException ex) {
        return ResponseEntity.badRequest().body(
                Map.of(ex.toString(), ex.getMessage())
        );
    }

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<Map<String, String>> handleServerError(
            InternalServerError ex ) {
        return ResponseEntity.internalServerError().body(
                Map.of("Server Error", ex.getMessage())
        );
    }

}
