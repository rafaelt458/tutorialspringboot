package com.laboratorio.matricula_service.model.entity;

import com.laboratorio.matricula_service.model.type.EstadoMatriculaEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "matriculas")
@Getter
@Setter
@NoArgsConstructor
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "estudiante_id", nullable = false)
    private Integer estudianteId;

    @Column(name = "curso_id", nullable = false)
    private String cursoId;

    @Column(name = "fecha_matricula", nullable = false)
    private LocalDateTime fechaMatricula;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoMatriculaEnum estado;

    @Column(name = "transaccion_id", nullable = false)
    private String transaccionId;

    public Matricula(Integer estudianteId, String cursoId) {
        this.estudianteId = estudianteId;
        this.cursoId = cursoId;
        this.fechaMatricula = LocalDateTime.now(ZoneId.systemDefault());
        this.estado = EstadoMatriculaEnum.CREADA;
        this.transaccionId = java.util.UUID.randomUUID().toString();
    }
}