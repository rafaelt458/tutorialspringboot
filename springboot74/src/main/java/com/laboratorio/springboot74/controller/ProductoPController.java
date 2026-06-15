package com.laboratorio.springboot74.controller;

import com.laboratorio.springboot74.model.dto.ProductoRequest;
import com.laboratorio.springboot74.model.dto.ProductoResponse;
import com.laboratorio.springboot74.service.ProductoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos/postgre")
public class ProductoPController {
    private final ProductoService productoService;

    public ProductoPController(@Qualifier("productoPServiceImpl") ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> findAllProductos() {
        List<ProductoResponse> productos = this.productoService.findAllProductos();
        return ResponseEntity.ok(productos);
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> createProducto(@RequestBody ProductoRequest request) {
        ProductoResponse producto = this.productoService.createProducto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(producto);
    }
}