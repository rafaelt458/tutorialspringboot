package com.laboratorio.springboot52.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabaseException extends RuntimeException {
    public DatabaseException(String message, Exception e) {
        super(message);
        log.error(message);
        log.error("Error: {}", e.getMessage());
        if (e.getCause() != null) {
            log.error("Causa: {}", e.getCause().getMessage());
        }
    }
}