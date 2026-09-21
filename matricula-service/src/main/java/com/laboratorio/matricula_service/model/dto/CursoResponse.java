package com.laboratorio.matricula_service.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CursoResponse {
    private String id;
    private String codigo;
    private String titulo;
    private String descripcion;
    private Integer horasDuration;
    private Integer maximoEstudiantes;
    private Integer estudiantesInscritos;
    private List<String> tags;
    private List<MaterialApoyoDto> materialesApoyo;
    private List<String> instructores;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
}