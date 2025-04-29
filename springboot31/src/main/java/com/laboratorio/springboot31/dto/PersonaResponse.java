package com.laboratorio.springboot31.dto;

import com.laboratorio.springboot31.model.Persona;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PersonaResponse {
    private Integer id;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private int experiencia;
    private double peso;
    private double estatura;
    private String telefono;
    private String email;

    public PersonaResponse(Persona persona) {
        this.id = persona.getId();
        this.nombres = persona.getNombres();
        this.apellidos = persona.getApellidos();
        this.fechaNacimiento = persona.getFechaNacimiento();
        this.experiencia = persona.getExperiencia();
        this.peso = persona.getPeso();
        this.estatura = persona.getEstatura();
        this.telefono = persona.getTelefono();
        this.email = persona.getEmail();
    }
}