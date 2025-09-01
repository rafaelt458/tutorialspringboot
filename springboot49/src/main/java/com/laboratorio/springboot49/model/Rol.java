package com.laboratorio.springboot49.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "roles")
@Getter @Setter @NoArgsConstructor
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50, unique = true)
    private String nombre;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "permisos_rol",
            joinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permiso_id", referencedColumnName = "id")
    )
    private Set<Permiso> permisos;

    public Rol(String nombre, Set<Permiso> permisos) {
        this.nombre = nombre;
        this.permisos = permisos;
    }
}