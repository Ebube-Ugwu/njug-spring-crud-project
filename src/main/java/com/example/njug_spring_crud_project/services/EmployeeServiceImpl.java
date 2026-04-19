package com.example.njug_spring_crud_project.services;

import com.example.njug_spring_crud_project.dtos.EmployeeDto;
import com.example.njug_spring_crud_project.dtos.NewEmployeeRequestDto;
import com.example.njug_spring_crud_project.entities.Employee;
import com.example.njug_spring_crud_project.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public EmployeeDto createEmployee(NewEmployeeRequestDto request) {

    }
}