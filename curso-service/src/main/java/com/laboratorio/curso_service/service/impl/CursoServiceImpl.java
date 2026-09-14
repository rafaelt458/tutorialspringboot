package com.laboratorio.curso_service.service.impl;

import com.laboratorio.curso_service.exception.ResourceNotFoundException;
import com.laboratorio.curso_service.model.dto.CursoRequest;
import com.laboratorio.curso_service.model.dto.CursoResponse;
import com.laboratorio.curso_service.model.dto.MaterialApoyoDto;
import com.laboratorio.curso_service.model.entity.Curso;
import com.laboratorio.curso_service.model.entity.MaterialApoyo;
import com.laboratorio.curso_service.repository.CursoRepository;
import com.laboratorio.curso_service.service.CursoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CursoServiceImpl implements CursoService {
    private final CursoRepository cursoRepository;

    private CursoResponse createCursoResponse(Curso curso) {

        List<String> tagsCopy = new ArrayList<>(curso.getTags());
        List<MaterialApoyoDto> materialesApoyoCopy = new ArrayList<>();
        for (MaterialApoyo ma : curso.getMaterialesApoyo()) {
            MaterialApoyoDto materialApoyoDto = new MaterialApoyoDto(ma.getTipo().toString(), ma.getTitulo(),
                    ma.getDescripcion(), ma.getUrlRecurso());
            materialesApoyoCopy.add(materialApoyoDto);
        }
        List<String> instructoresCopy = new ArrayList<>(curso.getInstructores());
        return new CursoResponse(curso.getId(), curso.getCodigo(), curso.getTitulo(), curso.getDescripcion(),
                curso.getHorasDuration(), curso.getMaximoEstudiantes(), curso.getEstudiantesInscritos(), tagsCopy,
                materialesApoyoCopy, instructoresCopy, curso.getActivo(), curso.getFechaCreation());
    }

    private Curso getByCodigo(String codigo) {
        return this.cursoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con el código: " + codigo));
    }

    @Override
    public List<CursoResponse> findAll() {
        List<Curso> cursos = this.cursoRepository.findAll();
        return cursos.stream()
                .map(this::createCursoResponse)
                .toList();
    }

    @Override
    public List<CursoResponse> findAllActivo() {
        List<Curso> cursos = this.cursoRepository.findByActivoTrue();
        return cursos.stream()
                .map(this::createCursoResponse)
                .toList();
    }

    @Override
    public CursoResponse findByCodigo(String codigo) {
        Curso curso = this.getByCodigo(codigo);
        return this.createCursoResponse(curso);
    }

    @Override
    @Transactional
    public CursoResponse create(CursoRequest request) {
        Optional<Curso> cursoDB = this.cursoRepository.findByCodigo(request.getCodigo());
        if (cursoDB.isPresent()) {
            log.info("El curso con el código {} ya existe. Se devuelven los datos recuperados.", request.getCodigo());
            return this.createCursoResponse(cursoDB.get());
        }

        Curso curso = new Curso(request);
        Curso cursoNuevo = this.cursoRepository.save(curso);

        return this.createCursoResponse(cursoNuevo);
    }

    @Override
    @Transactional
    public CursoResponse update(String codigo, CursoRequest request) {
        Curso curso = this.getByCodigo(codigo);

        if (curso.getEstudiantesInscritos() > 0) {
            String message = "No se puede actualizar el curso con código " + codigo + " porque tiene estudiantes inscritos.";
            throw new IllegalStateException(message);
        }

        curso.update(request);
        Curso cursoActualizado = this.cursoRepository.save(curso);

        return this.createCursoResponse(cursoActualizado);
    }

    @Override
    @Transactional
    public CursoResponse delete(String codigo) {
        Curso curso = this.getByCodigo(codigo);

        if (curso.getEstudiantesInscritos() > 0) {
            String message = "No se puede eliminar el curso con código " + codigo + " porque tiene estudiantes inscritos.";
            throw new IllegalStateException(message);
        }

        this.cursoRepository.delete(curso);

        return this.createCursoResponse(curso);
    }
}