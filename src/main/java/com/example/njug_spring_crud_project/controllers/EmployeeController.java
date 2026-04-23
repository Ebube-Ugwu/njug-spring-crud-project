package com.example.njug_spring_crud_project.controllers;

import com.example.njug_spring_crud_project.dtos.EmployeeResponseDto;
import com.example.njug_spring_crud_project.dtos.EmployeeRequestDto;
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
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
        var employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeRequestDto requestDto, UriComponentsBuilder uriComponentsBuilder) {
       var employeeDto = employeeService.createEmployee(requestDto);
       var uri = uriComponentsBuilder.path("employees/{id}")
               .buildAndExpand(employeeDto.id()).toUri();
       return ResponseEntity.created(uri).body(employeeDto);

    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployee(@PathVariable Long id) {
        var employeeDto = employeeService.getEmployee(id);
        return ResponseEntity.ok(employeeDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeRequestDto requestDto) {
        var employeeDto = employeeService.updateEmployee(id,
                requestDto);
        return ResponseEntity.ok(employeeDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeSoft(@PathVariable Long id) {
        employeeService.deleteEmployeeSoft(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/hard")
    public ResponseEntity<Void> deleteEmployeeHard(@PathVariable Long id) {
        employeeService.deleteEmployeeHard(id);
        return ResponseEntity.noContent().build();
    }
}
