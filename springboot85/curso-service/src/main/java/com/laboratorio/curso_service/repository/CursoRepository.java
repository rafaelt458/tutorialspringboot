package com.laboratorio.curso_service.repository;

import com.laboratorio.curso_service.model.entity.Curso;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CursoRepository extends MongoRepository<Curso, String> {
    List<Curso> findByActivoTrue();
    Optional<Curso> findByCodigo(String codigo);
}