package com.laboratorio.curso_service.service;

import com.laboratorio.curso_service.model.dto.CursoRequest;
import com.laboratorio.curso_service.model.dto.CursoResponse;

import java.util.List;

public interface CursoService {
    List<CursoResponse> findAll();
    List<CursoResponse> findAllActivo();
    CursoResponse findByCodigo(String code);
    CursoResponse create(CursoRequest request);
    CursoResponse update(String code, CursoRequest request);
    CursoResponse delete(String code);
}