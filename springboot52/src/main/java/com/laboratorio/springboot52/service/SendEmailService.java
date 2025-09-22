package com.laboratorio.springboot52.service;

import jakarta.mail.MessagingException;

public interface SendEmailService {
    void sendEmail(String to, String subject, String text);
    void sendEmail(String to, String subject, String text, String attachmentPath) throws MessagingException;
}