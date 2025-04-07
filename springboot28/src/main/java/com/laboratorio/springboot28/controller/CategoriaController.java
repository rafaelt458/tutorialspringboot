package com.laboratorio.springboot28.controller;

import com.laboratorio.springboot28.dto.CategoriaPage;
import com.laboratorio.springboot28.dto.CategoriaRequest;
import com.laboratorio.springboot28.dto.CategoriaResponse;
import com.laboratorio.springboot28.exception.InvalidOperationException;
import com.laboratorio.springboot28.exception.ResourceNotFoundException;
import com.laboratorio.springboot28.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {
    private final CategoriaService categoriaService;

    @GetMapping("/find")
    public ResponseEntity<?> findCategoria(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String nombre
    ) {
        if (((id == null) && (nombre == null)) || ((id != null) && (nombre != null))) {
            return ResponseEntity.badRequest().body("La búsqueda debe tener un parámetro");
        }

        Optional<CategoriaResponse> categoria;
        if (id != null) {
            categoria = this.categoriaService.findCategoriaById(id);
        } else {
            categoria = this.categoriaService.findOneByNombre(nombre);
        }
        if (categoria.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado la categoría buscada");
        }
        return ResponseEntity.ok(categoria.get());
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<CategoriaResponse> categorias = this.categoriaService.findAllOrderByNombreAsc();
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{infix}")
    public ResponseEntity<?> findByNombreContaining(@PathVariable String infix) {
        List<CategoriaResponse> categorias = this.categoriaService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/page")
    public ResponseEntity<?> findCategoriaPage(
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(name = "sortField", required = false, defaultValue = "nombre") String sortField,
            @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir) {

        CategoriaPage categoriaPage = this.categoriaService.findCategoriaPage(pageNumber, pageSize, sortField, sortDir);
        if (categoriaPage.getCategorias().isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(categoriaPage);
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> create(@RequestBody CategoriaRequest request) {
        CategoriaResponse categoria = this.categoriaService.createCategoria(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody CategoriaRequest request) {
        try {
            CategoriaResponse categoria = this.categoriaService.updateCategoria(id, request);
            return ResponseEntity.ok(categoria);
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
        try {
            if (!this.categoriaService.deleteCategoria(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe un categoría con id: " + id);
            }

            return ResponseEntity.ok("Se ha eliminado correctamente la categoría con id: " + id);
        } catch (InvalidOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ha ocurrido un error inesperado");
        }
    }
}