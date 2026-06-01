package com.laboratorio.springboot73.controller;

import com.laboratorio.springboot73.model.dto.EmpleadoRequest;
import com.laboratorio.springboot73.model.dto.EmpleadoResponse;
import com.laboratorio.springboot73.service.ifc.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
public class EmpleadoController {
    private final EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<EmpleadoResponse>> listEmpleados(){
        List<EmpleadoResponse> empleados = this.empleadoService.findAllEmpleados();
        return ResponseEntity.ok(empleados);
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponse> addEmpleado(@RequestBody EmpleadoRequest request){
        EmpleadoResponse empleadoNuevo = this.empleadoService.createEmpleado(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoNuevo);
    }
}