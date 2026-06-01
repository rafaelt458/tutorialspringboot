package com.laboratorio.springboot73.model.dto;

public record EmpleadoRequest(
        String nombre,
        String apellido,
        String departamento,
        Integer edad
) {
}