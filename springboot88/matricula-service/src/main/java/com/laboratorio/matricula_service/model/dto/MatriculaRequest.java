package com.laboratorio.matricula_service.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MatriculaRequest {
    @NotNull(message = "El id del estudiante es obligatorio")
    private Integer estudianteId;

    @NotBlank(message = "El código del curso es obligatorio")
    private String codigoCurso;
}