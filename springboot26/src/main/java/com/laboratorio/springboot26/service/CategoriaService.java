package com.laboratorio.springboot26.service;

import com.laboratorio.springboot26.dto.CategoriaPage;
import com.laboratorio.springboot26.dto.CategoriaRequest;
import com.laboratorio.springboot26.dto.CategoriaResponse;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    Optional<CategoriaResponse> findCategoriaById(Integer id);
    Optional<CategoriaResponse> findOneByNombre(String nombre);
    List<CategoriaResponse> findAllOrderByNombreAsc();
    List<CategoriaResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(String infix);
    CategoriaPage findCategoriaPage(int pageNumber, int pageSize, String sortField, String sortDir);
    CategoriaResponse createCategoria(CategoriaRequest request);
    CategoriaResponse updateCategoria(Integer id, CategoriaRequest request);
    boolean deleteCategoria(Integer id);
}