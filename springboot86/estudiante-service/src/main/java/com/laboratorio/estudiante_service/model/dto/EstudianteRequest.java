package com.laboratorio.estudiante_service.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EstudianteRequest {
    @NotBlank(message = "Los nombres del estudiante son obligatorios")
    @Size(min = 2, max = 50, message = "Los nombres del estudiante deben tener entre 2 y 50 caracteres")
    private String nombres;

    @NotBlank(message = "Los apellidos del estudiante son obligatorios")
    @Size(min = 2, max = 50, message = "Los apellidos del estudiante deben tener entre 2 y 50 caracteres")
    private String apellidos;

    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 8, max = 15, message = "El DNI del estudiante debe tener entre 8 y 15 caracteres")
    private String dni;

    @NotBlank(message = "La fecha de nacimiento del estudiante es obligatoria")
    @Pattern(
            regexp = "^\\d{2}-\\d{2}-\\d{4}$",
            message = "La fecha de nacimiento del estudiante debe tener el formato dd-MM-yyyy"
    )
    private String fechaNacimiento;

    @NotBlank(message = "El email del estudiante es obligatorio")
    @Email(message = "El email del estudiante debe tener un formato válido")
    @Size(max = 50, message = "El email del estudiante no puede superar 50 caracteres")
    private String email;

    @NotBlank(message = "La carrera del estudiante es obligatoria")
    @Size(max = 50, message = "La carrera del estudiante no puede superar 50 caracteres")
    private String carrera;
}