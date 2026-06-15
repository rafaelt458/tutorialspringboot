package com.laboratorio.springboot74.service;

import com.laboratorio.springboot74.model.dto.ProductoRequest;
import com.laboratorio.springboot74.model.dto.ProductoResponse;

import java.util.List;

public interface ProductoService {
    List<ProductoResponse> findAllProductos();
    ProductoResponse createProducto(ProductoRequest request);
}