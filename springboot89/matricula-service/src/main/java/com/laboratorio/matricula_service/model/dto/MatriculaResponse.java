package com.laboratorio.matricula_service.model.dto;

import com.laboratorio.matricula_service.model.entity.Matricula;
import com.laboratorio.matricula_service.model.type.EstadoMatriculaEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MatriculaResponse {
    private Integer id;
    private Integer estudianteId;
    private String cursoId;
    private LocalDateTime fechaMatricula;
    private EstadoMatriculaEnum estado;

    public MatriculaResponse(Matricula matricula) {
        this.id = matricula.getId();
        this.estudianteId = matricula.getEstudianteId();
        this.cursoId = matricula.getCursoId();
        this.fechaMatricula = matricula.getFechaMatricula();
        this.estado = matricula.getEstado();
    }
}