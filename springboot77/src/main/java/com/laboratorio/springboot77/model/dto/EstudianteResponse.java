package com.laboratorio.springboot77.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class EstudianteResponse {
    private Integer id;
    private String nombres;
    private String apellidos;
    private String dni;
    private LocalDate fechaNacimiento;
    private String email;
    private String carrera;

    public EstudianteResponse(Integer id, String nombres, String apellidos, String dni, LocalDate fechaNacimiento,
                              String email, String carrera) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.carrera = carrera;
    }
}