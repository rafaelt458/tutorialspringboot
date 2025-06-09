package com.laboratorio.springboot37.controller;

import com.laboratorio.springboot37.dto.ProductoRequest;
import com.laboratorio.springboot37.dto.ProductoResponse;
import com.laboratorio.springboot37.exception.ParameterException;
import com.laboratorio.springboot37.exception.ResourceNotFoundException;
import com.laboratorio.springboot37.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService productoService;

    @GetMapping("/find")
    public ResponseEntity<ProductoResponse> findCategoria(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String nombre
    ) {
        if (((id == null) && (nombre == null)) || ((id != null) && (nombre != null))) {
            throw new ParameterException("La búsqueda debe tener un parámetro");
        }

        Optional<ProductoResponse> producto;
        if (id != null) {
            producto = this.productoService.findProductoById(id);
        } else {
            producto = this.productoService.findOneByNombre(nombre);
        }
        if (producto.isEmpty()) {
            throw new ResourceNotFoundException("No existe el producto buscado");
        }

        return ResponseEntity.ok(producto.get());
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> findAll() {
        List<ProductoResponse> productos = this.productoService.findAllOrderByNombreAsc();
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{infix}")
    public ResponseEntity<List<ProductoResponse>> findByNombreContaining(@PathVariable String infix) {
        List<ProductoResponse> productos = this.productoService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<List<ProductoResponse>> findByCategoria(@PathVariable Integer id) {
        List<ProductoResponse> productos = this.productoService.findByCategoriaIdOrderByNombreAsc(id);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> create(@RequestBody @Valid ProductoRequest request) {
        ProductoResponse producto = this.productoService.createProducto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> update(@PathVariable Integer id, @RequestBody @Valid ProductoRequest request) {
        ProductoResponse producto = this.productoService.updateProducto(id, request);
        return ResponseEntity.ok(producto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        this.productoService.deleteProducto(id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe un producto con id: " + id);
    }
}