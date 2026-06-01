package com.laboratorio.springboot73.service.ifc;

import com.laboratorio.springboot73.model.dto.EmpleadoRequest;
import com.laboratorio.springboot73.model.dto.EmpleadoResponse;

import java.util.List;

public interface EmpleadoService {
    List<EmpleadoResponse> findAllEmpleados();
    EmpleadoResponse createEmpleado(EmpleadoRequest empleadoRequest);
}