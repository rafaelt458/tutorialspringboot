package com.laboratorio.springboot37.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException(String message) {
        super(message);
        log.error(message);
    }
}