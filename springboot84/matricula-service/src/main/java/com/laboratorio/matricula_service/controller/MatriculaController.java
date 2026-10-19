package com.laboratorio.matricula_service.controller;

import com.laboratorio.matricula_service.model.dto.MatriculaRequest;
import com.laboratorio.matricula_service.model.dto.MatriculaResponse;
import com.laboratorio.matricula_service.service.MatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matriculas")
@RequiredArgsConstructor
public class MatriculaController {
    private final MatriculaService matriculaService;

    @GetMapping("/{codigo}")
    public ResponseEntity<List<MatriculaResponse>> obtenerMatriculasPorCurso(@PathVariable String codigo) {
        List<MatriculaResponse> matriculas = this.matriculaService.getMatriculasByCurso(codigo);
        return ResponseEntity.ok(matriculas);
    }

    @GetMapping("/estado")
    public ResponseEntity<List<MatriculaResponse>> obtenerMatriculasPorEstado(
            @RequestParam("CodigoCurso") String codigoCurso,
            @RequestParam("Estado") String estado
    ) {
        List<MatriculaResponse> matriculas = this.matriculaService.getMatriculasByEstado(codigoCurso,estado);
        return ResponseEntity.ok(matriculas);
    }

    @PostMapping
    public ResponseEntity<MatriculaResponse> inscribirEstudiante(@Validated @RequestBody MatriculaRequest request){
        MatriculaResponse matriculaResponse = this.matriculaService.inscribirEstudiante(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(matriculaResponse);
    }
}