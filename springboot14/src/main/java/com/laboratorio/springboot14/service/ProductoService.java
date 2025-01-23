package com.laboratorio.springboot14.service;

import com.laboratorio.springboot14.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    Optional<Producto> findById(Integer id);

    List<Producto> findAll();

    Producto create(Producto producto);

    Optional<Producto> update(Integer id, Producto producto);

    boolean delete(Integer id);
}