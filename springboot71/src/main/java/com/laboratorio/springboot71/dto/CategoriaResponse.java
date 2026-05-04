package com.laboratorio.springboot71.dto;

import com.laboratorio.springboot71.model.Categoria;

import java.time.LocalDateTime;

public record CategoriaResponse(
        Integer id,
        String nombre,
        String descripcion,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion
) {
    public CategoriaResponse(Categoria categoria) {
        this(categoria.getId(), categoria.getNombre(), categoria.getDescripcion(),
                categoria.getFechaCreacion(), categoria.getFechaModificacion());
    }
}