package com.laboratorio.estudiante_service.service;

import com.laboratorio.estudiante_service.model.dto.EstudianteRequest;
import com.laboratorio.estudiante_service.model.dto.EstudianteResponse;

import java.util.List;

public interface EstudianteService {
    List<EstudianteResponse> findAll();
    EstudianteResponse findById(Integer id);
    EstudianteResponse create(EstudianteRequest request);
    EstudianteResponse update(Integer id, EstudianteRequest request);
    EstudianteResponse delete(Integer id);
}