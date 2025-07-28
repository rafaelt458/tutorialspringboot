package com.laboratorio.springboot40.controller;

import com.laboratorio.api.EmpleadoApi;
import com.laboratorio.dto.EmpleadoRequest;
import com.laboratorio.dto.EmpleadoResponse;
import com.laboratorio.springboot40.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmpleadoController implements EmpleadoApi {
    private final EmpleadoService empleadoService;

    @Override
    public ResponseEntity<List<EmpleadoResponse>> getAllEmpleados() {
        List<EmpleadoResponse> empleados = this.empleadoService.findAll();
        if (empleados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(empleados);
    }

    @Override
    public ResponseEntity<EmpleadoResponse> saveEmpleado(EmpleadoRequest empleadoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                this.empleadoService.createEmpleado(empleadoRequest));
    }

    @Override
    public ResponseEntity<List<EmpleadoResponse>> searchEmpleado(String departamento, Integer antiguedad) {
        return null;
    }
}