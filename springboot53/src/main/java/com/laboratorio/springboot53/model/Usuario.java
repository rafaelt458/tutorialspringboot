package com.laboratorio.springboot53.model;

import com.laboratorio.springboot53.dto.UsuarioRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Getter @Setter @NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false)
    private boolean activo;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "roles_usuario",
            joinColumns =  @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id")
    )
    private Set<Rol> roles;

    public Usuario(String username, String password, String email, String nombre, Set<Rol> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nombre = nombre;
        this.activo = true;
        this.fechaCreacion = LocalDateTime.now();
        this.roles = roles;
    }

    public Usuario(UsuarioRequest request, String password, Rol rol) {
        this.username = request.getUsername();
        this.password = password;
        this.email = request.getEmail();
        this.nombre = request.getNombre();
        this.activo = true;
        this.fechaCreacion = LocalDateTime.now();
        this.roles = new HashSet<>();
        this.roles.add(rol);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(username, usuario.username) && Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email);
    }
}