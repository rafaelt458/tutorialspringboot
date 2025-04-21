package com.laboratorio.springboot30.dao;

import com.laboratorio.springboot30.dto.ProductoRequest;
import com.laboratorio.springboot30.dto.ProductoResponse;

import java.util.List;

public interface ProductoDAO {
    ProductoResponse findById(Integer codigo);
    List<ProductoResponse> findAll();
    void save(ProductoRequest request);
    boolean update(Integer codigo, ProductoRequest request);
    boolean delete(Integer codigo);
}