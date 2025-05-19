package com.laboratorio.springboot34.controller;

import com.laboratorio.springboot34.model.dto.PersonaRequest;
import com.laboratorio.springboot34.model.dto.PersonaResponse;
import com.laboratorio.springboot34.service.PersonaService;
import com.laboratorio.springboot34.utils.ManejoErrores;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personas")
@RequiredArgsConstructor
public class PersonaController {
    private final PersonaService personaService;

    @GetMapping
    public ResponseEntity<List<PersonaResponse>> findAll() {
        return ResponseEntity.ok(this.personaService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createPersona(@RequestBody @Valid PersonaRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ManejoErrores.procesar(result));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(this.personaService.createPersona(request));
    }
}