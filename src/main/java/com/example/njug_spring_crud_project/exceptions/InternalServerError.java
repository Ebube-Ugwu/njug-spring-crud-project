package com.example.njug_spring_crud_project.exceptions;

public class InternalServerError extends RuntimeException {
    public InternalServerError() {
    }

    public InternalServerError(String message) {
        super(message);
    }
}
