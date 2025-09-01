package com.laboratorio.springboot49.repository;

import com.laboratorio.springboot49.model.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Integer> {
}