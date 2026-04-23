package com.example.njug_spring_crud_project.services;

import com.example.njug_spring_crud_project.dtos.EmployeeDto;
import com.example.njug_spring_crud_project.dtos.NewEmployeeRequestDto;
import com.example.njug_spring_crud_project.exceptions.DuplicateEmailException;
import com.example.njug_spring_crud_project.mappers.EmployeeMapper;
import com.example.njug_spring_crud_project.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    public EmployeeDto createEmployee(NewEmployeeRequestDto request) {
        if (employeeRepository.findByEmail(request.email()).isPresent()) {
            throw new DuplicateEmailException("employee already exists");
        }
        var employee = employeeMapper.toEntity(request);
        employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }
}