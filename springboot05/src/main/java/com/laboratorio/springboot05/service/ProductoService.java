package com.laboratorio.springboot05.service;

import com.laboratorio.springboot05.model.Producto;

import java.util.List;

public interface ProductoService {
    Producto findById(Integer id);

    List<Producto> findAll();

    Producto create(Producto producto);

    Producto update(Integer id, Producto producto);

    String delete(Integer id);
}