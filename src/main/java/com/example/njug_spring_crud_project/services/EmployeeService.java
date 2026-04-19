package com.example.njug_spring_crud_project.services;

import com.example.njug_spring_crud_project.entities.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
    public List<Employee> getAllEmployees();
}