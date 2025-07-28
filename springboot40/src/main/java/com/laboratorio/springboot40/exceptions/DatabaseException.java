package com.laboratorio.springboot40.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabaseException extends RuntimeException {
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
        log.error("Error: {}", cause.getMessage());
        if (cause.getCause() != null) {
            log.error("Cause: {}", cause.getCause().getMessage());
        }
    }
}