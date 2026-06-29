package com.laboratorio.springboot75.service;

import com.laboratorio.springboot75.model.dto.ProductoRequest;
import com.laboratorio.springboot75.model.dto.ProductoResponse;

import java.util.List;

public interface ProductoService {
    List<ProductoResponse> findAllProductos();
    ProductoResponse createProducto(ProductoRequest request);
}