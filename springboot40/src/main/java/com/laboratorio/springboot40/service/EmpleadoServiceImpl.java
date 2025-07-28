package com.laboratorio.springboot40.service;

import com.laboratorio.dto.EmpleadoRequest;
import com.laboratorio.dto.EmpleadoResponse;
import com.laboratorio.springboot40.exceptions.DatabaseException;
import com.laboratorio.springboot40.model.Empleado;
import com.laboratorio.springboot40.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {
    private final EmpleadoRepository empleadoRepository;

    @Override
    public List<EmpleadoResponse> findAll() {
        try {
            List<Empleado> empleados = this.empleadoRepository.findAll();
            return empleados.stream()
                    .map(this::createEmpleadoResponse)
                    .toList();
        } catch (Exception e) {
            throw new DatabaseException("Error generando la lista de empleados", e);
        }
    }

    @Override
    public EmpleadoResponse createEmpleado(EmpleadoRequest request) {
        try {
            Empleado empleado = new Empleado(request);
            Empleado empleadoNuevo = this.empleadoRepository.save(empleado);
            return this.createEmpleadoResponse(empleadoNuevo);
        } catch (Exception e) {
            throw new DatabaseException("Error creando un nuevo empleado", e);
        }
    }

    private EmpleadoResponse createEmpleadoResponse(Empleado e) {
        return new EmpleadoResponse(e.getId(), e.getNombre(), e.getApellido(), e.getDepartamento(),
                e.getFechaNacimiento(), e.getSalario(), e.getAntiguedad());
    }
}