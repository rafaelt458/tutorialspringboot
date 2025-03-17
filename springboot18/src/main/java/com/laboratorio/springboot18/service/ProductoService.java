package com.laboratorio.springboot18.service;

import com.laboratorio.springboot18.dto.ProductoRequest;
import com.laboratorio.springboot18.dto.ProductoResponse;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    Optional<ProductoResponse> findProductoById(Integer id);
    Optional<ProductoResponse> findOneByNombre(String nombre);
    List<ProductoResponse> findAllOrderByNombreAsc();
    List<ProductoResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(String infix);
    List<ProductoResponse> findByCategoriaIdOrderByNombreAsc(Integer categoriaId);
    ProductoResponse createProducto(ProductoRequest request);
    ProductoResponse updateProducto(Integer id, ProductoRequest request);
    boolean deleteProducto(Integer id);
}