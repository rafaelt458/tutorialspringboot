package com.laboratorio.springboot49.controller;

import com.laboratorio.springboot49.dto.CategoriaRequest;
import com.laboratorio.springboot49.dto.CategoriaResponse;
import com.laboratorio.springboot49.exception.ParameterException;
import com.laboratorio.springboot49.exception.ResourceNotFoundException;
import com.laboratorio.springboot49.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
// @PreAuthorize("denyAll()")
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
    // @PreAuthorize("hasAuthority('READ')")
    @PostAuthorize("returnObject.body.id == 1")
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
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<List<CategoriaResponse>> findAll() {
        this.showAuthenticationData();

        List<CategoriaResponse> categorias = this.categoriaService.findAllOrderByNombreAsc();
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{infix}")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<List<CategoriaResponse>> findByNombreContaining(@PathVariable String infix) {
        List<CategoriaResponse> categorias = this.categoriaService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('WRITE')")
    public ResponseEntity<CategoriaResponse> create(@RequestBody @Valid CategoriaRequest request) {
        CategoriaResponse categoria = this.categoriaService.createCategoria(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('WRITE')")
    public ResponseEntity<CategoriaResponse> update(@PathVariable Integer id, @RequestBody @Valid CategoriaRequest request) {
        CategoriaResponse categoria = this.categoriaService.updateCategoria(id, request);
        return ResponseEntity.ok(categoria);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        this.categoriaService.deleteCategoria(id);
        return ResponseEntity.ok("La categoría ha sido eliminada");
    }
}