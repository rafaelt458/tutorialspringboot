package com.laboratorio.springboot77.service;

import com.laboratorio.springboot77.model.dto.EstudianteRequest;
import com.laboratorio.springboot77.model.dto.EstudianteResponse;

import java.util.List;

public interface EstudianteService {
    List<EstudianteResponse> findAll();
    EstudianteResponse findById(Integer id);
    EstudianteResponse create(EstudianteRequest request);
    EstudianteResponse update(Integer id, EstudianteRequest request);
    EstudianteResponse delete(Integer id);
}