package com.example.njug_spring_crud_project.services;

import com.example.njug_spring_crud_project.dtos.EmailRequest;
import com.example.njug_spring_crud_project.dtos.EmployeeResponseDto;
import com.example.njug_spring_crud_project.dtos.EmployeeRequestDto;
import com.example.njug_spring_crud_project.dtos.ImportFileDto;
import com.example.njug_spring_crud_project.entities.Employee;
import com.example.njug_spring_crud_project.exceptions.DuplicateEmailException;
import com.example.njug_spring_crud_project.exceptions.EmployeeNotFoundException;
import com.example.njug_spring_crud_project.exceptions.InternalServerError;
import com.example.njug_spring_crud_project.mappers.EmployeeMapper;
import com.example.njug_spring_crud_project.repositories.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final EmailService emailService;

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
        emailService.sendSimpleMessage(
                employee.getEmail(),
                "Welcome Aboard",
                "Welcome Aboard");
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
            throw new IllegalStateException("employee is already " +
                    "inactive");
        }
        employee.setActive(false);
        employeeRepository.save(employee);
    }

    @Transactional
    @Override
    public List<EmployeeResponseDto> getEmployeesBySalaryRang(
            BigDecimal min,
            BigDecimal max) {
        List<Employee> employees = employeeRepository.findBySalaryRange(min, max);
        return employees.stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void importEmployeesFromExcel(ImportFileDto fileDto) {
        MultipartFile file = fileDto.file();
        if (file == null) {
            throw new IllegalStateException("file is null");
        }
        if (file.getOriginalFilename() == null ||
                !file.getOriginalFilename().matches(".*\\.xlsx$")) {
            throw new IllegalStateException("wrong file format");
        }

        try (Workbook wb = WorkbookFactory.create(file.getInputStream())) {
            for (Sheet sheet : wb) {
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        System.out.println(cell.getStringCellValue());
                    }
                }
            }
        } catch (IOException e) {
            throw new InternalServerError();
        }


    }
    @Override
    public void sendEmail(EmailRequest request) {
        try {
            emailService.sendSimpleMessage(
                    request.to(),
                    request.subject(),
                    request.text()
            );
        } catch (MailException e) {
            throw new MailException("mail not sent") {
            };
        }
    }
}