package com.laboratorio.springboot79.model.dto;

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

    public CursoResponse(String id, String codigo, String titulo, String descripcion, Integer horasDuration,
                          Integer maximoEstudiantes, Integer estudiantesInscritos, List<String> tags,
                          List<MaterialApoyoDto> materialesApoyo, List<String> instructores, Boolean activo,
                          LocalDateTime fechaCreacion) {
        this.id = id;
        this.codigo = codigo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.horasDuration = horasDuration;
        this.maximoEstudiantes = maximoEstudiantes;
        this.estudiantesInscritos = estudiantesInscritos;
        this.tags = tags;
        this.materialesApoyo = materialesApoyo;
        this.instructores = instructores;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
    }
}