package com.laboratorio.springboot26.controller;

import com.laboratorio.springboot26.dto.ProductoPage;
import com.laboratorio.springboot26.dto.ProductoRequest;
import com.laboratorio.springboot26.dto.ProductoResponse;
import com.laboratorio.springboot26.exception.InvalidOperationException;
import com.laboratorio.springboot26.exception.ResourceNotFoundException;
import com.laboratorio.springboot26.service.ProductoService;
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
    public ResponseEntity<?> findCategoria(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String nombre
    ) {
        if (((id == null) && (nombre == null)) || ((id != null) && (nombre != null))) {
            return ResponseEntity.badRequest().body("La búsqueda debe tener un parámetro");
        }

        Optional<ProductoResponse> producto;
        if (id != null) {
            producto = this.productoService.findProductoById(id);
        } else {
            producto = this.productoService.findOneByNombre(nombre);
        }
        if (producto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado el producto buscado");
        }

        return ResponseEntity.ok(producto.get());
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<ProductoResponse> productos = this.productoService.findAllOrderByNombreAsc();
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{infix}")
    public ResponseEntity<?> findByNombreContaining(@PathVariable String infix) {
        List<ProductoResponse> productos = this.productoService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<?> findByCategoria(@PathVariable Integer id) {
        List<ProductoResponse> productos = this.productoService.findByCategoriaIdOrderByNombreAsc(id);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);
    }

    @GetMapping("/page")
    public ResponseEntity<?> findProductoPage(
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(name = "sortField", required = false, defaultValue = "nombre") String sortField,
            @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir) {

        ProductoPage productoPage = this.productoService.findProductoPage(pageNumber, pageSize, sortField, sortDir);
        if (productoPage.getProductos().isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productoPage);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductoRequest request) {
        try {
            ProductoResponse producto = this.productoService.createProducto(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(producto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ha ocurrido un error inesperado");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ProductoRequest request) {
        try {
            ProductoResponse producto = this.productoService.updateProducto(id, request);
            return ResponseEntity.ok(producto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (InvalidOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ha ocurrido un error inesperado");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        if (!this.productoService.deleteProducto(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe un producto con id: " + id);
        }

        return ResponseEntity.ok("Se ha eliminado correctamente el producto con id: " + id);
    }
}