package com.laboratorio.SpringBoot39.service;

import com.laboratorio.SpringBoot39.dto.EmpleadoResponse;
import com.laboratorio.SpringBoot39.model.Empleado;
import com.laboratorio.SpringBoot39.repository.EmpleadoRepository;
import com.laboratorio.SpringBoot39.repository.EmpleadoRepository2;
import com.laboratorio.SpringBoot39.repository.specification.EmpleadoSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {
    // private final EmpleadoRepository empleadoRepository;
    private final EmpleadoRepository2 empleadoRepository;

    @Override
    public List<EmpleadoResponse> findEmpleados(String nombre, String apellido, String departamento, Integer edad) {
        // List<Empleado> empleados = this.empleadoRepository.findEmpleados(nombre, apellido, departamento, edad);

        Specification<Empleado> spec = Specification
                .where(EmpleadoSpecifications.conNombre(nombre))
                .and(EmpleadoSpecifications.conApellido(apellido))
                .and(EmpleadoSpecifications.conDepartamento(departamento))
                .and((EmpleadoSpecifications.conEdad(edad)));

        List<Empleado> empleados = this.empleadoRepository.findAll(spec);

        /* List<Empleado> empleados = this.empleadoRepository.findAll(
                EmpleadoSpecifications.empleadoSearch(nombre, apellido, departamento, edad)); */

        List<EmpleadoResponse> empleadosResponse = new ArrayList<>();
        for (Empleado empleado : empleados) {
            EmpleadoResponse empleadoResponse = new EmpleadoResponse(empleado);
            empleadosResponse.add(empleadoResponse);
        }
        return empleadosResponse;
    }
}