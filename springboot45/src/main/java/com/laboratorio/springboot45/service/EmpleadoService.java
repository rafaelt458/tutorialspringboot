package com.laboratorio.springboot45.service;

import com.laboratorio.springboot45.dto.EmpleadoResponse;

import java.util.List;

public interface EmpleadoService {
    List<EmpleadoResponse> findEmpleados(String nombre, String apellido,
                                         String departamento, Integer edad);
}