package com.example.njug_spring_crud_project.controllers;

import com.example.njug_spring_crud_project.dtos.EmployeeDto;
import com.example.njug_spring_crud_project.dtos.NewEmployeeRequestDto;
import com.example.njug_spring_crud_project.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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

    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody NewEmployeeRequestDto requestDto, UriComponentsBuilder uriComponentsBuilder) {
       var employeeDto = employeeService.createEmployee(requestDto);
       var uri = uriComponentsBuilder.path("employees/{id}")
               .buildAndExpand(employeeDto.id()).toUri();
       return ResponseEntity.created(uri).body(employeeDto);

    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable Long id) {
        var employeeDto = employeeService.getEmployee(id);
        return ResponseEntity.ok(employeeDto);
    }
}
