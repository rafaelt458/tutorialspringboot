package com.laboratorio.springboot7.controller;

import com.laboratorio.springboot7.model.Producto;
import com.laboratorio.springboot7.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductoRestController {
    private final ProductoService productoService;

    /* public ProductoRestController(@Qualifier("implementacion2") ProductoService productoService) {
        this.productoService = productoService;
    } */

    @GetMapping(value = "/productos/{id}")
    public Producto findById(@PathVariable  Integer id) {
        return this.productoService.findById(id);
    }

    @GetMapping(value = "/productos")
    public List<Producto> findAll() {
        return this.productoService.findAll();
    }

    @PostMapping(value = "/productos")
    public Producto create(@RequestBody  Producto producto) {
        return this.productoService.create(producto);
    }

    @PutMapping(value = "/productos/{id}")
    public Producto update(@PathVariable Integer id, @RequestBody  Producto producto) {
        return this.productoService.update(id, producto);
    }

    @DeleteMapping(value = "/productos/{id}")
    public String delete(@PathVariable Integer id) {
        return this.productoService.delete(id);
    }
}