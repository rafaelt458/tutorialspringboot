package com.laboratorio.springboot40.service;

import com.laboratorio.dto.EmpleadoRequest;
import com.laboratorio.dto.EmpleadoResponse;

import java.util.List;

public interface EmpleadoService {
    List<EmpleadoResponse> findAll();
    EmpleadoResponse createEmpleado(EmpleadoRequest request);
}