package com.laboratorio.SpringBoot39.dto;

import com.laboratorio.SpringBoot39.model.Empleado;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class EmpleadoResponse {
    private Integer id;
    private String nombre;
    private String apellido;
    private String departamento;
    private Integer edad;

    public EmpleadoResponse(Empleado e) {
        this.id = e.getId();
        this.nombre = e.getNombre();
        this.apellido = e.getApellido();
        this.departamento = e.getDepartamento();
        this.edad = e.getEdad();
    }
}