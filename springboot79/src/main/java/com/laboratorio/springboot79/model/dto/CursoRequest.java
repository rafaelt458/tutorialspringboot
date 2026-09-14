package com.laboratorio.springboot79.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CursoRequest {
    @NotBlank(message = "El código del curso es obligatorio.")
    @Size(min = 3, message = "El código del curso debe tener al menos 3 caracteres.")
    private String codigo;

    @NotBlank(message = "El título del curso es obligatorio.")
    @Size(min = 10, message = "El título del curso debe tener al menos 10 caracteres.")
    private String titulo;

    private String descripcion;

    @Min(value = 8, message = "La duración debe ser al menos de 8 horas.")
    private Integer horasDuration;

    @Min(value = 10, message = "El número mínimo de estudiantes es 10")
    @Max(value = 25, message = "El número máximo de estudiantes es 25")
    private Integer maximoEstudiantes;

    private List<String> tags;

    private List<@Valid MaterialApoyoDto> materialesApoyo;

    private List<String> instructores;
}