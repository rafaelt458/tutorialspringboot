package com.laboratorio.springboot71.service;

import com.laboratorio.springboot71.dto.CategoriaResponse;

import java.util.List;

public interface CategoriaService {
    List<CategoriaResponse> findAllCategoria();
    CategoriaResponse findCategoriaByNombre(String nombre);
    CategoriaResponse addCategoria(String nombre, String descripcion);
}