package com.example.njug_spring_crud_project.exceptions;

public class InvalidFileFormatException extends RuntimeException {
    public InvalidFileFormatException(String message) {
        super(message);
    }
}
