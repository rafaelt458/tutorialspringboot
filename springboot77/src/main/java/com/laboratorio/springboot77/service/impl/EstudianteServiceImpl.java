package com.laboratorio.springboot77.service.impl;

import com.laboratorio.springboot77.exception.ResourceNotFoundException;
import com.laboratorio.springboot77.model.dto.EstudianteRequest;
import com.laboratorio.springboot77.model.dto.EstudianteResponse;
import com.laboratorio.springboot77.model.entity.Estudiante;
import com.laboratorio.springboot77.repository.EstudianteRepository;
import com.laboratorio.springboot77.service.EstudianteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstudianteServiceImpl implements EstudianteService {
    private static final String ERROR_EMAIL = "El email ya está registrado para otro estudiante. No se puede continuar con la operación.";

    private final EstudianteRepository estudianteRepository;

    private EstudianteResponse createEstudianteResponse(Estudiante estudiante) {
        return new EstudianteResponse(
                estudiante.getId(),
                estudiante.getNombres(),
                estudiante.getApellidos(),
                estudiante.getDni(),
                estudiante.getFechaNacimiento(),
                estudiante.getEmail(),
                estudiante.getCarrera()
        );
    }

    @Override
    public List<EstudianteResponse> findAll() {
        List<Estudiante> estudiantes = this.estudianteRepository.findAll();
        return estudiantes.stream()
                .map(this::createEstudianteResponse)
                .toList();
    }

    private Estudiante getById(Integer id) {
        return this.estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con el id: " + id));
    }

    @Override
    public EstudianteResponse findById(Integer id) {
        Estudiante estudiante = this.getById(id);
        return this.createEstudianteResponse(estudiante);
    }

    @Override
    @Transactional
    public EstudianteResponse create(EstudianteRequest request) {
        Optional<Estudiante> estudianteDB = this.estudianteRepository.findByDni(request.getDni());
        if (estudianteDB.isPresent()) {
            log.info("El estudiante ya existe. Se devuelven los datos recuperados.");
            return this.createEstudianteResponse(estudianteDB.get());
        }

        estudianteDB = this.estudianteRepository.findByEmail(request.getEmail());
        if (estudianteDB.isPresent()) {
            throw new IllegalStateException(ERROR_EMAIL);
        }

        Estudiante estudiante = new Estudiante(request);
        Estudiante nuevo = this.estudianteRepository.save(estudiante);
        return this.createEstudianteResponse(nuevo);
    }

    @Override
    @Transactional
    public EstudianteResponse update(Integer id, EstudianteRequest request) {
        Estudiante estudiante = this.getById(id);

        Optional<Estudiante> estudianteDB = this.estudianteRepository.findByEmail(request.getEmail());
        if (estudianteDB.isPresent() && !estudianteDB.get().equals(estudiante)) {
            throw new IllegalStateException(ERROR_EMAIL);
        }

        estudiante.update(request);
        Estudiante actualizado = this.estudianteRepository.save(estudiante);
        return this.createEstudianteResponse(actualizado);
    }

    @Override
    @Transactional
    public EstudianteResponse delete(Integer id) {
        Estudiante estudiante = this.getById(id);
        this.estudianteRepository.delete(estudiante);
        return this.createEstudianteResponse(estudiante);
    }
}