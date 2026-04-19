package com.example.njug_spring_crud_project.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
