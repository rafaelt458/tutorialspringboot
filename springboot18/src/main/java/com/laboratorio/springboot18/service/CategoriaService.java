package com.laboratorio.springboot18.service;

import com.laboratorio.springboot18.dto.CategoriaRequest;
import com.laboratorio.springboot18.dto.CategoriaResponse;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    Optional<CategoriaResponse> findCategoriaById(Integer id);
    Optional<CategoriaResponse> findOneByNombre(String nombre);
    List<CategoriaResponse> findAllOrderByNombreAsc();
    List<CategoriaResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(String infix);
    CategoriaResponse createCategoria(CategoriaRequest request);
    CategoriaResponse updateCategoria(Integer id, CategoriaRequest request);
    boolean deleteCategoria(Integer id);
}