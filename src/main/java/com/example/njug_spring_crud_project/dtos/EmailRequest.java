package com.example.njug_spring_crud_project.dtos;

public record EmailRequest(
        String to,
        String subject,
        String text
) { }
