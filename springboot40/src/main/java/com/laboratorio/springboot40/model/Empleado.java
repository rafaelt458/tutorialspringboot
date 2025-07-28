package com.laboratorio.springboot40.model;

import com.laboratorio.dto.EmpleadoRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "Empleados")
@Getter @Setter @NoArgsConstructor
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30, nullable = false)
    private String nombre;

    @Column(length = 30, nullable = false)
    private String apellido;

    @Column(length = 50, nullable = false)
    private String departamento;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false)
    private double salario;

    @Transient
    private int antiguedad;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    public int getAntiguedad() {
        if (fechaIngreso == null) {
            return 0;
        }

        this.antiguedad = Period.between(this.fechaIngreso, LocalDate.now()).getYears();

        return this.antiguedad;
    }

    public Empleado(EmpleadoRequest request) {
        this.nombre = request.getNombre();
        this.apellido = request.getApellido();
        this.departamento = request.getDepartamento();
        this.fechaNacimiento = request.getFechaNacimiento();
        this.salario = request.getSalario();
        this.fechaIngreso = LocalDate.now();
    }
}