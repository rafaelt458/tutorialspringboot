package com.laboratorio.springboot08.controller;

import com.laboratorio.springboot08.model.Producto;
import com.laboratorio.springboot08.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductoRestController {
    // private final ProductoService productoService;

    private final List<ProductoService> productoService;
    private final int pos = 1;

    @GetMapping(value = "/productos/{id}")
    public Producto findById(@PathVariable  Integer id) {
        return this.productoService.get(pos).findById(id);
    }

    @GetMapping(value = "/productos")
    public List<Producto> findAll() {
        return this.productoService.get(pos).findAll();
    }

    @PostMapping(value = "/productos")
    public Producto create(@RequestBody  Producto producto) {
        return this.productoService.get(pos).create(producto);
    }

    @PutMapping(value = "/productos/{id}")
    public Producto update(@PathVariable Integer id, @RequestBody  Producto producto) {
        return this.productoService.get(pos).update(id, producto);
    }

    @DeleteMapping(value = "/productos/{id}")
    public String delete(@PathVariable Integer id) {
        return this.productoService.get(pos).delete(id);
    }
}