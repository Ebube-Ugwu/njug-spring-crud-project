package com.example.njug_spring_crud_project.services;

import org.springframework.mail.MailException;

public interface EmailService {
    public  void sendSimpleMessage(
            String to,
            String subject,
            String message) throws MailException;
}
