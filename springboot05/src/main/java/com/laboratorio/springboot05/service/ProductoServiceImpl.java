package com.laboratorio.springboot05.service;

import com.laboratorio.springboot05.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoServiceImpl implements ProductoService {
    private List<Producto> productos = new ArrayList<>(
            List.of(
                    new Producto(1, "Mouse", 25),
                    new Producto(2, "Teclado", 22),
                    new Producto(3, "Monitor", 121)
            )
    );

    @Override
    public Producto findById(Integer id) {
        return productos.stream()
                .filter(p -> p.getCodigo().equals(id))
                .findAny()
                .orElseThrow();
    }

    @Override
    public List<Producto> findAll() {
        return productos;
    }

    @Override
    public Producto create(Producto producto) {
        productos.add(producto);
        return producto;
    }

    @Override
    public Producto update(Integer id, Producto producto) {
        Producto productoAModificar = this.findById(id);

        productoAModificar.setNombre(producto.getNombre());
        productoAModificar.setPrecio(producto.getPrecio());

        return productoAModificar;
    }

    @Override
    public String delete(Integer id) {
        Producto productoAEliminar = this.findById(id);

        productos.remove(productoAEliminar);

        return "Se ha eliminado el producto " + productoAEliminar.getNombre();
    }
}