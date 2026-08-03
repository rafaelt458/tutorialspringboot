package com.laboratorio.springboot77.model.dto;

import java.time.LocalDateTime;

public record EstudianteError(
        LocalDateTime timestamp,
        int status,
        String error,
        String path
) {
}