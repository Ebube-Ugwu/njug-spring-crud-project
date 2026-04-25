package com.example.njug_spring_crud_project.services;

import com.example.njug_spring_crud_project.dtos.EmailRequest;
import com.example.njug_spring_crud_project.dtos.EmployeeResponseDto;
import com.example.njug_spring_crud_project.dtos.EmployeeRequestDto;
import com.example.njug_spring_crud_project.dtos.ImportFileDto;
import com.example.njug_spring_crud_project.entities.Employee;
import com.example.njug_spring_crud_project.exceptions.DuplicateEmailException;
import com.example.njug_spring_crud_project.exceptions.EmployeeNotFoundException;
import com.example.njug_spring_crud_project.exceptions.InternalServerError;
import com.example.njug_spring_crud_project.exceptions.InvalidFileFormatException;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final EmailService emailService;
    private final PdfService pdfService;

    @Override
    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @Override
    public EmployeeResponseDto createEmployee(EmployeeRequestDto request) {
        if (employeeRepository.findByEmail(request.getEmail()).isPresent()) {
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
            throw new InvalidFileFormatException("file is null");
        }
        if (file.getOriginalFilename() == null ||
                !file.getOriginalFilename().matches(".*\\.xlsx$")) {
            throw new InvalidFileFormatException("wrong file format");
        }

        List<EmployeeRequestDto> newEmployees = extractEmployees(file);

        for (var employeeDto : newEmployees) {
            var employee = employeeMapper.toEntity(employeeDto);
            if (employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
                // Skips duplicate emails rather than throwing an exception and cancelling
                // the operation, so it can import valid rows
                continue;
            }
            employeeRepository.save(employee);
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

    private List<EmployeeRequestDto> extractEmployees(MultipartFile file) {
        List<EmployeeRequestDto> newEmployees = new ArrayList<>();

        try (Workbook wb = WorkbookFactory.create(file.getInputStream())) {
            for (Sheet sheet : wb) {
                for (Row row : sheet) {
                    var employeeDto = new EmployeeRequestDto();
                    if (row.getRowNum() == 0) {
                        continue;
                    }
                    Cell firstCell = row.getCell(0);
                    if (firstCell == null || firstCell.getCellType() == CellType.BLANK) {
                        continue;
                    }
                    for (Cell cell : row) {
////                 firstName lastName email department salary dateOfJoining active
                        switch (cell.getColumnIndex()) {
                            case 0:
                                employeeDto.setFirstName(cell.getStringCellValue());
                                break;
                            case 1:
                                employeeDto.setLastName(cell.getStringCellValue());
                                break;
                            case 2:
                                employeeDto.setEmail(cell.getStringCellValue());
                                break;
                            case 3:
                                employeeDto.setDepartment(cell.getStringCellValue());
                                break;
                            case 4:
                                employeeDto.setSalary(BigDecimal.valueOf(cell.getNumericCellValue()));
                                break;
                            case 5:
                                employeeDto.setDateOfJoining(LocalDate.now());
                                break;
                            case 6:
                                employeeDto.setActive(false);
                                break;
                        }
                    }
                    newEmployees.add(employeeDto);
                }
            }
        } catch (IOException e) {
            throw new InternalServerError();
        }
        return newEmployees;
    }
}