package com.laboratorio.springboot33.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PersonaRequest {
    @NotNull(message = "Los nombres no pueden ser nulos")
    @Size(min = 3, max = 40, message = "Los nombres deben tener un mínimo de 3 caracteres y un máximo de 40")
    private String nombres;

    @NotNull(message = "Los apellidos no pueden ser nulos")
    @Size(min = 3, max = 40, message = "Los apellidos deben tener un mínimo de 3 caracteres y un máximo de 40")
    private String apellidos;

    private String fechaNacimiento;

    @Min(value = 0, message = "La experiencia mínima es de 0 años")
    @Max(value = 70, message = "La experiencia máxima es de 70 años")
    private int experiencia;

    @Digits(integer = 3, fraction = 2, message = "El peso debe tener como máximo 3 enteros y 2 decimales")
    private double peso;

    @Digits(integer = 3, fraction = 1, message = "La estatura debe tener como máximo 3 enteros y 1 decimal")
    private double estatura;

    @Valid
    private DatosContactoRequest datosContacto;
}