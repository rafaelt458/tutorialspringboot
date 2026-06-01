package com.laboratorio.springboot73.model.entity;

import com.laboratorio.springboot73.model.dto.EmpleadoRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "empleados")
@Getter @Setter @NoArgsConstructor
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30, nullable = false)
    private String nombre;

    @Column(length = 30, nullable = false)
    private String apellido;

    @Column(length = 40, nullable = false)
    private String departamento;

    @Column(nullable = false)
    private Integer edad;

    public Empleado(EmpleadoRequest request) {
        this.nombre = request.nombre();
        this.apellido = request.apellido();
        this.departamento = request.departamento();
        this.edad = request.edad();
    }
}