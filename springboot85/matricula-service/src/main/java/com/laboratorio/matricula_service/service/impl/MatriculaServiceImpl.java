package com.laboratorio.matricula_service.service.impl;

import com.laboratorio.framework.exception.RateLimitExceededException;
import com.laboratorio.framework.exception.ResourceNotFoundException;
import com.laboratorio.framework.model.dto.CursoResponse;
import com.laboratorio.framework.model.dto.EstudianteResponse;
import com.laboratorio.framework.model.event.CupoCursoRechazadoEvent;
import com.laboratorio.framework.model.event.CupoCursoReservadoEvent;
import com.laboratorio.matricula_service.model.dto.MatriculaRequest;
import com.laboratorio.matricula_service.model.dto.MatriculaResponse;
import com.laboratorio.matricula_service.model.entity.Matricula;
import com.laboratorio.matricula_service.model.type.EstadoMatriculaEnum;
import com.laboratorio.matricula_service.repository.MatriculaRepository;
import com.laboratorio.matricula_service.service.MatriculaService;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatriculaServiceImpl implements MatriculaService {
    private final MatriculaRepository matriculaRepository;
    private final EstudianteClientService estudianteClientService;
    private final CursoClientService cursoClientService;
    private final MatriculaEventPublisher matriculaEventPublisher;

    private Matricula getMatriculaById(Integer id) {
        return this.matriculaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado la matrícula con id: " + id));
    }

    @Override
    public List<MatriculaResponse> getMatriculasByCurso(String codigoCurso) {
        // Validar la existencia del curso
        CursoResponse curso = this.cursoClientService.getCursoByCodigo(codigoCurso);

        List<Matricula> matriculas = this.matriculaRepository.findByCursoId(curso.getId());
        return matriculas.stream()
                .map(MatriculaResponse::new)
                .toList();
    }

    @Override
    public List<MatriculaResponse> getMatriculasByEstado(String codigoCurso, String estado) {
        EstadoMatriculaEnum estadoCurso =  EstadoMatriculaEnum.from(estado);

        // Validar la existencia del curso
        CursoResponse curso = this.cursoClientService.getCursoByCodigo(codigoCurso);

        List<Matricula> matriculas = this.matriculaRepository.findByCursoIdAndEstado(curso.getId(), estadoCurso);
        return matriculas.stream()
                .map(MatriculaResponse::new)
                .toList();
    }

    @Override
    @RateLimiter(
            name = "matriculaRateLimiter",
            fallbackMethod = "matriculaRateLimitFallback"
    )
    public MatriculaResponse inscribirEstudiante(MatriculaRequest request) {
        // Validar existencia del estudiante
        EstudianteResponse estudiante = this.estudianteClientService.getEstudianteById(request.getEstudianteId());
        log.info("Se ha encontrado el estudiante {}, {}, con el id: {}", estudiante.getNombres(), estudiante.getApellidos(), request.getEstudianteId());

        // Validar la existencia del curso
        CursoResponse curso = this.cursoClientService.getCursoByCodigo(request.getCodigoCurso());
        log.info("Se ha encontrado el curso {}, con el id: {}", curso.getTitulo(), curso.getId());

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

        // Publicar el evento de registro de la matrícula
        this.matriculaEventPublisher.publishMatriculaCreadaEvent(nuevaMatricula);

        return new MatriculaResponse(nuevaMatricula);
    }

    public MatriculaResponse matriculaRateLimitFallback(MatriculaRequest request, RequestNotPermitted exception) {
        String message = "Se ha superado el límite de solicitudes de matrículas. Por favor, inténtelo de nuevo más tarde.";

        log.warn("No se pudo procesar la matrícula para el estudiante con id: {} en el curso con código: {}",
                request.getEstudianteId(), request.getCodigoCurso());

        throw new RateLimitExceededException(message, exception);
    }

    @Override
    public void confirmarMatricula(CupoCursoReservadoEvent event) {
        Matricula matricula = this.getMatriculaById(event.matriculaId());
        matricula.setEstado(EstadoMatriculaEnum.CONFIRMADA);
        this.matriculaRepository.save(matricula);
        log.info("Se ha confirmado la matrícula con id: {}", matricula.getId());
    }

    @Override
    public void cancelarMatricula(CupoCursoRechazadoEvent event) {
        Matricula matricula = this.getMatriculaById(event.matriculaId());
        matricula.setEstado(EstadoMatriculaEnum.CANCELADA);
        this.matriculaRepository.save(matricula);
        log.warn("Se ha rechazado la matrícula con id: {}", matricula.getId());
    }
}