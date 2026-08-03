package com.laboratorio.springboot77.controller;

import com.laboratorio.springboot77.model.dto.EstudianteRequest;
import com.laboratorio.springboot77.model.dto.EstudianteResponse;
import com.laboratorio.springboot77.service.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {
    private final EstudianteService estudianteService;

    @GetMapping
    public ResponseEntity<List<EstudianteResponse>> findAll() {
        List<EstudianteResponse> estudiantes = this.estudianteService.findAll();
        if  (estudiantes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(estudiantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteResponse> findById(@PathVariable String id) {
        EstudianteResponse estudiante = this.estudianteService.findById(Integer.valueOf(id));
        return ResponseEntity.ok(estudiante);
    }

    @PostMapping
    public ResponseEntity<EstudianteResponse> create(@Validated @RequestBody EstudianteRequest request) {
        EstudianteResponse estudiante = this.estudianteService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(estudiante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstudianteResponse> update(@PathVariable Integer id,
                                                     @Validated @RequestBody EstudianteRequest request) {
        EstudianteResponse estudiante = this.estudianteService.update(id, request);
        return ResponseEntity.ok(estudiante);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EstudianteResponse> delete(@PathVariable Integer id) {
        EstudianteResponse estudiante = this.estudianteService.delete(id);
        return ResponseEntity.ok(estudiante);
    }
}