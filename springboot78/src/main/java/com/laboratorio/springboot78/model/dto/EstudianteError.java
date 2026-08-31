package com.laboratorio.springboot78.model.dto;

import java.time.LocalDateTime;

public record EstudianteError(
        LocalDateTime timestamp,
        int status,
        String error,
        String path
) {
}