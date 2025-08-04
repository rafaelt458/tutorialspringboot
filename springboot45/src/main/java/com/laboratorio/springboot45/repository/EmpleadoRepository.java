package com.laboratorio.springboot45.repository;

import com.laboratorio.springboot45.model.Empleado;

import java.util.List;

public interface EmpleadoRepository {
    List<Empleado> findEmpleados(String nombre, String apellido,
                                 String departamento, Integer edad);
}