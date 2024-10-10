package com.laboratorio.springboot04.controller;

import com.laboratorio.springboot04.model.Producto;
import com.laboratorio.springboot04.service.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductoRestController {
    private ProductoService productoService = new ProductoService();

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