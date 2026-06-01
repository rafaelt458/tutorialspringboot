package com.laboratorio.springboot73.service.impl;

import com.laboratorio.springboot73.model.dto.EmpleadoRequest;
import com.laboratorio.springboot73.model.dto.EmpleadoResponse;
import com.laboratorio.springboot73.model.entity.Empleado;
import com.laboratorio.springboot73.repository.EmpleadoRepository;
import com.laboratorio.springboot73.service.ifc.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {
    private final EmpleadoRepository empleadoRepository;

    @Override
    public List<EmpleadoResponse> findAllEmpleados() {
        List<Empleado> empleados = this.empleadoRepository.findAll();
        return empleados.stream()
                .map(EmpleadoResponse::new)
                .toList();
    }

    @Override
    public EmpleadoResponse createEmpleado(EmpleadoRequest empleadoRequest) {
        Empleado empleado = new Empleado(empleadoRequest);
        Empleado empleadoNuevo = this.empleadoRepository.save(empleado);

        return new EmpleadoResponse(empleadoNuevo);
    }
}