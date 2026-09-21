package com.laboratorio.matricula_service.model.dto;

import java.time.LocalDateTime;

public record MatriculaError(
        LocalDateTime timestamp,
        int status,
        String error,
        String path
) {
}