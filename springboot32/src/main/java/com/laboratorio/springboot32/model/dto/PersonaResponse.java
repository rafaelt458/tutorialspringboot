package com.laboratorio.springboot32.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PersonaResponse {
    private Integer codigo;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private int experiencia;
    private double peso;
    private double estatura;
    private String telefono;
    private String email;
}