package com.laboratorio.springboot73.model.dto;

import com.laboratorio.springboot73.model.entity.Empleado;

public record EmpleadoResponse(
        Integer id,
        String nombre,
        String apellido,
        String departamento,
        Integer edad
) {
    public EmpleadoResponse(Empleado empleado) {
        this(empleado.getId(), empleado.getNombre(), empleado.getApellido(), empleado.getDepartamento(), empleado.getEdad());
    }
}