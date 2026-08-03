package com.laboratorio.springboot77.model.entity;

import com.laboratorio.springboot77.model.dto.EstudianteRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "estudiantes")
@Getter
@Setter
@NoArgsConstructor
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String nombres;

    @Column(nullable = false, length = 50)
    private String apellidos;

    @Column(name = "tipo_documento_id", nullable = false)
    private Integer tipoDocumentoId;

    @Column(name = "numero_documento", nullable = false, length = 15, unique = true)
    private String dni;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 50)
    private String carrera;

    public Estudiante(EstudianteRequest request) {
        this.nombres = request.getNombres();
        this.apellidos = request.getApellidos();
        this.dni = request.getDni();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.fechaNacimiento = LocalDate.parse(request.getFechaNacimiento(), formatter);
        this.email = request.getEmail();
        this.carrera = request.getCarrera();
        this.tipoDocumentoId = 1;
    }

    public void update(EstudianteRequest request) {
        this.nombres = request.getNombres();
        this.apellidos = request.getApellidos();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.fechaNacimiento = LocalDate.parse(request.getFechaNacimiento(), formatter);
        this.email = request.getEmail();
        this.carrera = request.getCarrera();
    }
}