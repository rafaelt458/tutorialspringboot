package com.laboratorio.springboot53.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidUserExcepcion extends RuntimeException {
    public InvalidUserExcepcion(String message) {
        super(message);
        log.error(message);
    }
}