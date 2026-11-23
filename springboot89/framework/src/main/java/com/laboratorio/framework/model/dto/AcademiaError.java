package com.laboratorio.framework.model.dto;

import java.time.LocalDateTime;

public record AcademiaError(
        LocalDateTime timestamp,
        int status,
        String error,
        String path
) {
}