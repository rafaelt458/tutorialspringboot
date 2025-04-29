package com.laboratorio.springboot31.controller;

import com.laboratorio.springboot31.dto.PersonaRequest;
import com.laboratorio.springboot31.service.PersonaService;
import com.laboratorio.springboot31.utils.ManejoErrores;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/personas")
@RequiredArgsConstructor
public class PersonaController {
    private final PersonaService personaService;

    @PostMapping
    public ResponseEntity<?> createPersona(@RequestBody @Valid PersonaRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ManejoErrores.procesar(result));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(this.personaService.createPersona(request));
    }
}