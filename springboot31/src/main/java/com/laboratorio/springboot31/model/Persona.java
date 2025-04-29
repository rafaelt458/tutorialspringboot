package com.laboratorio.springboot31.model;

import com.laboratorio.springboot31.dto.PersonaRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "personas")
@Getter @Setter @NoArgsConstructor
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 40, nullable = false)
    private String nombres;

    @Column(length = 40, nullable = false)
    private String apellidos;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false)
    private int experiencia;

    @Column(nullable = false)
    private double peso;

    @Column(nullable = false)
    private double estatura;

    @Column(length = 20, nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String email;

    public Persona(PersonaRequest request) {
        this.nombres = request.getNombres();
        this.apellidos = request.getApellidos();
        this.fechaNacimiento = request.getFechaNacimiento();
        this.experiencia = request.getExperiencia();
        this.peso = request.getPeso();
        this.estatura = request.getEstatura();
        this.telefono = request.getTelefono();
        this.email = request.getEmail();
    }
}