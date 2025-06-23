package com.laboratorio.SpringBoot39.repository;

import com.laboratorio.SpringBoot39.model.Empleado;

import java.util.List;

public interface EmpleadoRepository {
    List<Empleado> findEmpleados(String nombre, String apellido,
                                 String departamento, Integer edad);
}