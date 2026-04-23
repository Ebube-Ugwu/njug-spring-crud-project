package com.example.njug_spring_crud_project.services;

import com.example.njug_spring_crud_project.dtos.EmployeeResponseDto;
import com.example.njug_spring_crud_project.dtos.EmployeeRequestDto;
import com.example.njug_spring_crud_project.exceptions.DuplicateEmailException;
import com.example.njug_spring_crud_project.exceptions.EmployeeNotFoundException;
import com.example.njug_spring_crud_project.mappers.EmployeeMapper;
import com.example.njug_spring_crud_project.repositories.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @Override
    public EmployeeResponseDto createEmployee(EmployeeRequestDto request) {
        if (employeeRepository.findByEmail(request.email()).isPresent()) {
            throw new DuplicateEmailException("employee already exists");
        }
        var employee = employeeMapper.toEntity(request);
        employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Override
    public EmployeeResponseDto getEmployee(Long id) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);
        return employeeMapper.toDto(employee);
    }

    @Override
    @Transactional
    public EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto requestDto) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);
        employeeMapper.update(requestDto, employee);
        employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Transactional
    @Override
    public void deleteEmployeeHard(Long id) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);
        if (employee.getActive()) {
            throw new IllegalStateException("employee is still active");
        }
        employeeRepository.delete(employee);
    }

    @Transactional
    @Override
    public void deleteEmployeeSoft(Long id) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);
        if (!employee.getActive()) {
            throw  new IllegalStateException("employee is already " +
                    "inactive");
        }
        employee.setActive(false);
        employeeRepository.save(employee);
    }
}