package com.laboratorio.springboot05.controller;

import com.laboratorio.springboot05.model.Producto;
import com.laboratorio.springboot05.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductoRestController {
    // @Autowired
    private final ProductoService productoService;

    /* @Autowired
    public void setProductoService(ProductoService productoService) {
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