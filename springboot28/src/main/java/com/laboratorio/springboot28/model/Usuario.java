package com.laboratorio.springboot28.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter @Setter @NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = true, length = 40)
    private String username;

    @Column(nullable = true, length = 40)
    private String password;

    @Column(nullable = false)
    private boolean activo;

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
        this.activo = true;
    }
}