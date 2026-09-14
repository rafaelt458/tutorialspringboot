package com.laboratorio.springboot79.model.entity;

import com.laboratorio.springboot79.model.dto.CursoRequest;
import com.laboratorio.springboot79.model.dto.MaterialApoyoDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "cursos")
@Getter
@Setter
@NoArgsConstructor
public class Curso {
    @Id
    private String id;

    private String codigo;
    private String titulo;
    private String descripcion;
    @Field("horas_duracion")
    private Integer horasDuration;
    @Field("maximo_estudiantes")
    private Integer maximoEstudiantes;
    @Field("estudiantes_inscritos")
    private Integer estudiantesInscritos;
    private List<String> tags;
    @Field("materiales_apoyo")
    private List<MaterialApoyo> materialesApoyo;
    private List<String> instructores;
    private Boolean activo;
    @Field("fecha_creation")
    private LocalDateTime fechaCreation;
    @Field("numero_accesos")
    private int numeroAccesos;

    public Curso(CursoRequest request) {
        this.codigo = request.getCodigo();
        this.titulo = request.getTitulo();
        this.descripcion = request.getDescripcion();
        this.horasDuration = request.getHorasDuration();
        this.maximoEstudiantes = request.getMaximoEstudiantes();
        this.estudiantesInscritos = 0;
        this.tags = request.getTags();
        this.materialesApoyo = this.mapearMaterialesApoyo(request.getMaterialesApoyo());
        this.instructores = request.getInstructores();
        this.activo = true;
        this.fechaCreation = LocalDateTime.now(ZoneId.systemDefault());
        this.numeroAccesos = 0;
    }

    private List<MaterialApoyo> mapearMaterialesApoyo(List<MaterialApoyoDto> materialesApoyo) {
        if (materialesApoyo == null) {
            return new ArrayList<>();
        }
        return materialesApoyo.stream()
                .map(MaterialApoyo::new)
                .toList();
    }

    public void update(CursoRequest request) {
        this.titulo = request.getTitulo();
        this.descripcion = request.getDescripcion();
        this.horasDuration = request.getHorasDuration();
        this.maximoEstudiantes = request.getMaximoEstudiantes();
        this.tags = request.getTags();
        this.materialesApoyo = this.mapearMaterialesApoyo(request.getMaterialesApoyo());
        this.instructores = request.getInstructores();
    }
}