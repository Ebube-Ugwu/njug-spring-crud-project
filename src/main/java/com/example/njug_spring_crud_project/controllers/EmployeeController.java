package com.example.njug_spring_crud_project.controllers;

import com.example.njug_spring_crud_project.dtos.EmployeeDto;
import com.example.njug_spring_crud_project.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        var employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }
}
