package com.laboratorio.matricula_service.model.type;

import java.util.Arrays;

public enum EstadoMatriculaEnum {
    CREADA,
    CONFIRMADA,
    CANCELADA,
    ROLLBACK;

    public static EstadoMatriculaEnum from(String value) {
        if (value == null) {
            throw new IllegalArgumentException("El estado de la matrícula no puede ser nulo.");
        }

        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Estado de matrícula inválido: '" + value +
                                        "'. Los valores permitidos son: CREADA, CONFIRMADA, CANCELADA, ROLLBACK."
                        )
                );
    }
}