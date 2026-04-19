package com.example.njug_spring_crud_project.dtos;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record EmployeeDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String department,
        BigDecimal salary,
        LocalDate dateOfJoining,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
        ) { }
