package com.laboratorio.curso_service.model.dto;

import java.time.LocalDateTime;

public record CursoError(
        LocalDateTime timestamp,
        int status,
        String error,
        String path
) {
}