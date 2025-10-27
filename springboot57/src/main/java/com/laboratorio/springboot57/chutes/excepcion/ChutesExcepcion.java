package com.laboratorio.springboot57.chutes.excepcion;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChutesExcepcion extends RuntimeException {
    public ChutesExcepcion(String message) {
        super(message);
        log.error("Mensaje: {}", message);
    }

    public ChutesExcepcion(String message, Throwable cause) {
        super(message, cause);
        log.error("Error: {}", message);
        log.error("Detalle: {}", cause.getMessage());
    }
}