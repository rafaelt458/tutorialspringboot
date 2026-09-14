package com.laboratorio.springboot79.repository;

import com.laboratorio.springboot79.model.entity.Curso;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CursoRepository extends MongoRepository<Curso, String> {
    List<Curso> findByActivoTrue();
    Optional<Curso> findByCodigo(String codigo);
}