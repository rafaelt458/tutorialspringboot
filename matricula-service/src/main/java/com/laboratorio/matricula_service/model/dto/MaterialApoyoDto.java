package com.laboratorio.matricula_service.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MaterialApoyoDto {
    private String tipo;
    private String titulo;
    private String descripcion;
    private String urlRecurso;
}