package com.laboratorio.springboot54.repository;

import com.laboratorio.springboot54.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findOneByNombre(String nombre);
}