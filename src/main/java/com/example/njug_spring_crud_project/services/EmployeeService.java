package com.example.njug_spring_crud_project.services;

import com.example.njug_spring_crud_project.dtos.EmployeeDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EmployeeService {
    public List<EmployeeDto> getAllEmployees();
}