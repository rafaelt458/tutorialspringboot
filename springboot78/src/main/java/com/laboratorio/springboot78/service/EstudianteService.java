package com.laboratorio.springboot78.service;

import com.laboratorio.springboot78.model.dto.EstudianteRequest;
import com.laboratorio.springboot78.model.dto.EstudianteResponse;

import java.util.List;

public interface EstudianteService {
    List<EstudianteResponse> findAll();
    EstudianteResponse findById(Integer id);
    EstudianteResponse create(EstudianteRequest request);
    EstudianteResponse update(Integer id, EstudianteRequest request);
    EstudianteResponse delete(Integer id);
}