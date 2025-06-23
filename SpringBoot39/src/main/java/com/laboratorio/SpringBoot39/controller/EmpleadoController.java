package com.laboratorio.SpringBoot39.controller;

import com.laboratorio.SpringBoot39.dto.EmpleadoResponse;
import com.laboratorio.SpringBoot39.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
public class EmpleadoController {
    private final EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<EmpleadoResponse>> findEmpleados(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String apellido,
            @RequestParam(required = false) String departamento,
            @RequestParam(required = false) Integer edad) {

        List<EmpleadoResponse> empleados = this.empleadoService.
                findEmpleados(nombre, apellido, departamento, edad);
        if (empleados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(empleados);
    }
}