package com.laboratorio.springboot50.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParameterException extends RuntimeException {
    public ParameterException(String message) {
        super(message);
        log.error(message);
    }
}