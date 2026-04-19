package com.example.njug_spring_crud_project.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDate;

public record NewEmployeeRequestDto(
        @NotBlank(message = "first name is required")
        String firstName,
        @NotBlank(message = "last name is required")
        String lastName,
        @NotBlank(message = "email is required")
        @Email
        String email,
        String department,
        BigDecimal salary,
        LocalDate dateOfJoining,
        Boolean active
){}
