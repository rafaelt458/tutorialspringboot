package com.laboratorio.matricula_service.service.impl;

import com.laboratorio.matricula_service.client.CursoClient;
import com.laboratorio.matricula_service.client.EstudianteClient;
import com.laboratorio.matricula_service.exception.ResourceNotFoundException;
import com.laboratorio.matricula_service.model.dto.CursoResponse;
import com.laboratorio.matricula_service.model.dto.EstudianteResponse;
import com.laboratorio.matricula_service.model.dto.MatriculaRequest;
import com.laboratorio.matricula_service.model.dto.MatriculaResponse;
import com.laboratorio.matricula_service.model.entity.Matricula;
import com.laboratorio.matricula_service.model.type.EstadoMatriculaEnum;
import com.laboratorio.matricula_service.repository.MatriculaRepository;
import com.laboratorio.matricula_service.service.MatriculaService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatriculaServiceImpl implements MatriculaService {
    private final MatriculaRepository matriculaRepository;
    private final EstudianteClient estudianteClient;
    private final CursoClient cursoClient;

    @Override
    public List<MatriculaResponse> getMatriculasByCurso(String codigoCurso) {
        // Validar la existencia del curso
        CursoResponse curso;
        try {
            curso = this.cursoClient.getCursoByCodigo(codigoCurso);
        } catch (FeignException.NotFound _) {
            throw new ResourceNotFoundException("No se ha encontrado el curso con código: " + codigoCurso);
        }

        List<Matricula> matriculas = this.matriculaRepository.findByCursoId(curso.getId());
        return matriculas.stream()
                .map(MatriculaResponse::new)
                .toList();
    }

    @Override
    public List<MatriculaResponse> getMatriculasByEstado(String codigoCurso, String estado) {
        EstadoMatriculaEnum estadoCurso =  EstadoMatriculaEnum.from(estado);

        // Validar la existencia del curso
        CursoResponse curso;
        try {
            curso = this.cursoClient.getCursoByCodigo(codigoCurso);
        } catch (FeignException.NotFound _) {
            throw new ResourceNotFoundException("No se ha encontrado el curso con código: " + codigoCurso);
        }

        List<Matricula> matriculas = this.matriculaRepository.findByCursoIdAndEstado(curso.getId(), estadoCurso);
        return matriculas.stream()
                .map(MatriculaResponse::new)
                .toList();
    }

    @Override
    public MatriculaResponse inscribirEstudiante(MatriculaRequest request) {
        // Validar existencia del estudiante
        EstudianteResponse estudiante;
        try {
            estudiante = this.estudianteClient.getEstudianteById(request.getEstudianteId());
        } catch (FeignException.NotFound _) {
            throw new ResourceNotFoundException("No se ha encontrado el estudiante con id: " + request.getEstudianteId());
        }

        log.info("Se ha encontrado el estudiante {}, {}, con el id: {}",
                estudiante.getNombres(), estudiante.getApellidos(), request.getEstudianteId());

        // Validar la existencia del curso
        CursoResponse curso;
        try {
            curso = this.cursoClient.getCursoByCodigo(request.getCodigoCurso());
        } catch (FeignException.NotFound _) {
            throw new ResourceNotFoundException("No se ha encontrado el curso con código: " + request.getCodigoCurso());
        }

        log.info("Se ha encontrado el curso {}, con el id: {}",
                curso.getTitulo(), curso.getId());

        // Validar los cupos disponibles
        if (curso.getEstudiantesInscritos() >= curso.getMaximoEstudiantes()) {
            throw new IllegalStateException("No hay cupos disponibles para el curso con código: " + request.getCodigoCurso());
        }

        // Evitar una doble inscripción del estudiante en el mismo curso
        if (this.matriculaRepository.existsByEstudianteIdAndCursoId(request.getEstudianteId(), curso.getId())) {
            throw new IllegalStateException("El estudiante con id: " + request.getEstudianteId() + " ya está inscrito en el curso con código: " + request.getCodigoCurso());
        }

        // Registrar la matrícula
        Matricula matricula = new Matricula(request.getEstudianteId(), curso.getId());
        Matricula nuevaMatricula = this.matriculaRepository.save(matricula);

        return new MatriculaResponse(nuevaMatricula);
    }
}