package com.laboratorio.SpringBoot39.repository;

import com.laboratorio.SpringBoot39.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository2 extends JpaRepository<Empleado, Integer>,
        JpaSpecificationExecutor<Empleado> {
}