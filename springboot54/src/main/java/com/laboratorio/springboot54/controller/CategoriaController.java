package com.laboratorio.springboot54.controller;

import com.laboratorio.springboot54.dto.CategoriaRequest;
import com.laboratorio.springboot54.dto.CategoriaResponse;
import com.laboratorio.springboot54.exception.ParameterException;
import com.laboratorio.springboot54.exception.ResourceNotFoundException;
import com.laboratorio.springboot54.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor @Slf4j
public class CategoriaController {
    private final CategoriaService categoriaService;

    private void showAuthenticationData() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        log.info("Usuario conectado: {}", authentication.getPrincipal());
        log.info("Permisos usuario: {}", authentication.getAuthorities());
        log.info("Detalles: {}", authentication.getDetails());
        log.info("¿Autenticado? : {}", authentication.isAuthenticated());
    }

    @GetMapping("/find")
    public ResponseEntity<CategoriaResponse> findCategoria(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String nombre
    ) {
       this.showAuthenticationData();

        if (((id == null) && (nombre == null)) || ((id != null) && (nombre != null))) {
            throw new ParameterException("La búsqueda debe tener un parámetro");
        }

        Optional<CategoriaResponse> categoria;
        if (id != null) {
            categoria = this.categoriaService.findCategoriaById(id);
        } else {
            categoria = this.categoriaService.findOneByNombre(nombre);
        }
        if (categoria.isEmpty()) {
            throw new ResourceNotFoundException("No existe la categoria buscada");
        }
        return ResponseEntity.ok(categoria.get());
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> findAll() {
        this.showAuthenticationData();

        List<CategoriaResponse> categorias = this.categoriaService.findAllOrderByNombreAsc();
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{infix}")
    public ResponseEntity<List<CategoriaResponse>> findByNombreContaining(@PathVariable String infix) {
        List<CategoriaResponse> categorias = this.categoriaService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> create(@RequestBody @Valid CategoriaRequest request) {
        CategoriaResponse categoria = this.categoriaService.createCategoria(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> update(@PathVariable Integer id, @RequestBody @Valid CategoriaRequest request) {
        CategoriaResponse categoria = this.categoriaService.updateCategoria(id, request);
        return ResponseEntity.ok(categoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        this.categoriaService.deleteCategoria(id);
        return ResponseEntity.ok("La categoría ha sido eliminada");
    }
}