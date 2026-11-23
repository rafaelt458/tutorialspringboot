package com.laboratorio.matricula_service.repository;

import com.laboratorio.matricula_service.model.entity.Matricula;
import com.laboratorio.matricula_service.model.type.EstadoMatriculaEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {
    List<Matricula> findByCursoId(String cursoId);
    List<Matricula> findByCursoIdAndEstado(String cursoId, EstadoMatriculaEnum estado);
    boolean existsByEstudianteIdAndCursoId(Integer estudianteId, String cursoId);
}