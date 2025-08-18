package com.laboratorio.springboot46.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParameterException extends RuntimeException {
    public ParameterException(String message) {
        super(message);
        log.error(message);
    }
}