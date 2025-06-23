package com.laboratorio.SpringBoot39.service;

import com.laboratorio.SpringBoot39.dto.EmpleadoResponse;

import java.util.List;

public interface EmpleadoService {
    List<EmpleadoResponse> findEmpleados(String nombre, String apellido,
                                         String departamento, Integer edad);
}