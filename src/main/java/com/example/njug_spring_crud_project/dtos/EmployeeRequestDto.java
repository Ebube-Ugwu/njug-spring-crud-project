package com.example.njug_spring_crud_project.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


@Data
@NoArgsConstructor
public class EmployeeRequestDto {
        private  @NotBlank(message = "first name is required") String firstName;
        private  @NotBlank(message = "last name is required") String lastName;
        private  @NotBlank(message = "email is required")
        @Email String email;
        private  String department;
        private  BigDecimal salary;
        private  LocalDate dateOfJoining;
        private  Boolean active;
}
