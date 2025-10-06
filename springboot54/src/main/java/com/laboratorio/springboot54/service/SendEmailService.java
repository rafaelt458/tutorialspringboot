package com.laboratorio.springboot54.service;

import jakarta.mail.MessagingException;

public interface SendEmailService {
    void sendEmail(String to, String subject, String text);
    void sendEmail(String to, String subject, String text, String attachmentPath) throws MessagingException;
}