package com.example.njug_spring_crud_project.dtos;

import java.util.List;

public record ImportResultDto(
        int successCount,
        int failureCount,
        List<String> errors
) { }
