package com.example.njug_spring_crud_project.controllers;

import com.example.njug_spring_crud_project.dtos.EmailRequest;
import com.example.njug_spring_crud_project.dtos.EmployeeResponseDto;
import com.example.njug_spring_crud_project.dtos.EmployeeRequestDto;
import com.example.njug_spring_crud_project.dtos.ImportFileDto;
import com.example.njug_spring_crud_project.exceptions.InternalServerError;
import com.example.njug_spring_crud_project.services.EmployeeService;
import com.example.njug_spring_crud_project.services.PdfService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final PdfService pdfService;

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
        var employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/salary-range")
    public ResponseEntity<List<EmployeeResponseDto>> getEmployeesBySalaryRange(
            @RequestParam("min") BigDecimal min,
            @RequestParam("max") BigDecimal max) {
        var employees = employeeService.getEmployeesBySalaryRang(min,
                max);
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

    @PostMapping("/send-mail")
    public ResponseEntity<Void> sendMail(@RequestBody EmailRequest request) {
        employeeService.sendEmail(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/import", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> importEmployees(@ModelAttribute ImportFileDto file) {
    employeeService.importEmployeesFromExcel(file);
    return ResponseEntity.ok().build();
    }

    @GetMapping("/export/pdf")
    public  void exportToPDF(HttpServletResponse response) {
        response.setContentType("application/pdf");
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=\"employee_report_" + timestamp + ".pdf");

        var employeeList = employeeService.getAllEmployees();
        try {
            pdfService.exportToPDF(employeeList, response.getOutputStream());
        } catch (IOException e) {
            throw new InternalServerError("error while generating pdf");
        }
    }
}
