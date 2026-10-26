package com.laboratorio.framework.model.event;

public record MatriculaCreadaEvent(
        Integer matriculaId,
        Integer estudianteId,
        String cursoId,
        String transaccionId
) {
}