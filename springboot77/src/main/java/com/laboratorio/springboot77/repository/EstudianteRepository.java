package com.laboratorio.springboot77.repository;

import com.laboratorio.springboot77.model.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {
    Optional<Estudiante> findByDni(String dni);
    Optional<Estudiante> findByEmail(String email);
}