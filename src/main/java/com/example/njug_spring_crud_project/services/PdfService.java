package com.example.njug_spring_crud_project.services;

import com.example.njug_spring_crud_project.dtos.EmployeeResponseDto;

import java.io.OutputStream;
import java.util.List;

public interface PdfService {
    void exportToPDF(List<EmployeeResponseDto> employees,
                     OutputStream out);
}
