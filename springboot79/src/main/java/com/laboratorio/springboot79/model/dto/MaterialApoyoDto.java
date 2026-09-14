package com.laboratorio.springboot79.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MaterialApoyoDto {
    @NotBlank(message = "El tipo de material de apoyo es obligatorio.")
    @Pattern(
            regexp = "VIDEO|PDF|EXERCISE|LINK",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "El tipo de material de apoyo debe ser uno de los siguientes: VIDEO, PDF, EXERCISE o LINK."
    )
    private String tipo;

    @NotBlank(message = "El título del material de apoyo es obligatorio.")
    private String titulo;

    private String descripcion;

    @NotBlank(message = "La URL del recurso es obligatoria.")
    private String urlRecurso;

    public MaterialApoyoDto(String tipo, String titulo, String descripcion, String urlRecurso) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.urlRecurso = urlRecurso;
    }
}