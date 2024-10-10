package com.laboratorio.springboot04.service;

import com.laboratorio.springboot04.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoService {
    private List<Producto> productos = new ArrayList<>(
            List.of(
                    new Producto(1, "Mouse", 25),
                    new Producto(2, "Teclado", 22),
                    new Producto(3, "Monitor", 121)
            )
    );

    public Producto findById(Integer id) {
        return productos.stream()
                .filter(p -> p.getCodigo().equals(id))
                .findAny()
                .orElseThrow();
    }

    public List<Producto> findAll() {
        return productos;
    }

    public Producto create(Producto producto) {
        productos.add(producto);
        return producto;
    }

    public Producto update(Integer id, Producto producto) {
        Producto productoAModificar = this.findById(id);

        productoAModificar.setNombre(producto.getNombre());
        productoAModificar.setPrecio(producto.getPrecio());

        return productoAModificar;
    }

    public String delete(Integer id) {
        Producto productoAEliminar = this.findById(id);

        productos.remove(productoAEliminar);

        return "Se ha eliminado el producto " + productoAEliminar.getNombre();
    }
}