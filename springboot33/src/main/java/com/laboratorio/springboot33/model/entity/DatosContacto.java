package com.laboratorio.springboot33.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "datos_contacto")
@Getter @Setter @NoArgsConstructor
public class DatosContacto {
    @Id
    private Integer id;

    @Column(nullable = false)
    private String direccion;

    @Column(length = 20, nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String email;

    @MapsId
    @OneToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;
}