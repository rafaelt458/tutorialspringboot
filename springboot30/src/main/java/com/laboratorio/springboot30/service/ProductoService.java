package com.laboratorio.springboot30.service;

import com.laboratorio.springboot30.dto.ProductoRequest;
import com.laboratorio.springboot30.dto.ProductoResponse;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    Optional<ProductoResponse> findById(Integer codigo);
    List<ProductoResponse> findAll();
    void save(ProductoRequest request);
    boolean update(Integer codigo, ProductoRequest request);
    boolean delete(Integer codigo);
}