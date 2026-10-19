package com.laboratorio.curso_service.model.type;

import java.util.Arrays;

public enum MaterialApoyoEnum {
    VIDEO, PDF, EXERCISE, LINK;

    public static MaterialApoyoEnum from(String value) {
        if (value == null) {
            throw new IllegalArgumentException("El tipo de material de apoyo no puede ser nulo.");
        }

        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Tipo de material de apoyo inválido: '" + value +
                                        "'. Los valores permitidos son: VIDEO, PDF, EXERCISE, LINK."
                        )
                );
    }
}