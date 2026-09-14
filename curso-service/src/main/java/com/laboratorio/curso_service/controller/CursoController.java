package com.laboratorio.curso_service.controller;

import com.laboratorio.curso_service.model.dto.CursoRequest;
import com.laboratorio.curso_service.model.dto.CursoResponse;
import com.laboratorio.curso_service.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {
    private final CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<CursoResponse>> findAll() {
        List<CursoResponse> cursos = this.cursoService.findAll();
        if (cursos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/activos")
    public ResponseEntity<List<CursoResponse>> findAllActive() {
        List<CursoResponse> cursos = this.cursoService.findAllActivo();
        if (cursos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<CursoResponse> findByCode(@PathVariable String codigo) {
        CursoResponse curso = this.cursoService.findByCodigo(codigo);
        return ResponseEntity.ok(curso);
    }

    @PostMapping
    public ResponseEntity<CursoResponse> create(@Validated @RequestBody CursoRequest request) {
        CursoResponse curso = this.cursoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(curso);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<CursoResponse> update(@PathVariable String codigo,
                                                 @Validated @RequestBody CursoRequest request) {
        CursoResponse curso = this.cursoService.update(codigo, request);
        return ResponseEntity.ok(curso);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<CursoResponse> delete(@PathVariable String codigo) {
        CursoResponse curso = this.cursoService.delete(codigo);
        return ResponseEntity.ok(curso);
    }
}