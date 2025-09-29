package com.laboratorio.springboot53.dto;

import com.laboratorio.springboot53.model.Categoria;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CategoriaResponse {
    private Integer id;
    private String nombre;

    public CategoriaResponse(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public CategoriaResponse(Categoria categoria) {
        this.id = categoria.getId();
        this.nombre = categoria.getNombre();
    }
}