package com.example.njug_spring_crud_project.services;

import com.example.njug_spring_crud_project.dtos.EmployeeResponseDto;
import com.example.njug_spring_crud_project.dtos.EmployeeRequestDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface EmployeeService {
    public List<EmployeeResponseDto> getAllEmployees();
    public EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto);
    public EmployeeResponseDto getEmployee(Long id);

    EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto requestDto);

    @Transactional
    void deleteEmployeeHard(Long id);

    @Transactional
    void deleteEmployeeSoft(Long id);
}