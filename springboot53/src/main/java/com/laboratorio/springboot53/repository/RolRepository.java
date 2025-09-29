package com.laboratorio.springboot53.repository;

import com.laboratorio.springboot53.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findOneByNombre(String nombre);
}