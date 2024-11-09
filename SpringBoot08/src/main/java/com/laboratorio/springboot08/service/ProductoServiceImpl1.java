package com.laboratorio.springboot08.service;

import com.laboratorio.springboot08.model.Producto;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// @Profile("prod")
@Service
@Order(1)
public class ProductoServiceImpl1 implements ProductoService {
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

        return "Impl1: Se ha eliminado el producto " + productoAEliminar.getNombre();
    }
}