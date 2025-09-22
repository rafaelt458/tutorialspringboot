package com.laboratorio.springboot52.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.laboratorio.springboot52.dto.CategoriaRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "categorias")
@Getter @Setter @NoArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50, unique = true)
    private String nombre;

    @OneToMany(mappedBy = "categoria")
    @JsonManagedReference
    private List<Producto> productos;

    public Categoria(CategoriaRequest request) {
        this.nombre = request.getNombre();
    }

    public Categoria(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}