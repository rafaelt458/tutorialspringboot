package com.laboratorio.springboot79.service;

import com.laboratorio.springboot79.model.dto.CursoRequest;
import com.laboratorio.springboot79.model.dto.CursoResponse;

import java.util.List;

public interface CursoService {
    List<CursoResponse> findAll();
    List<CursoResponse> findAllActivo();
    CursoResponse findByCodigo(String code);
    CursoResponse create(CursoRequest request);
    CursoResponse update(String code, CursoRequest request);
    CursoResponse delete(String code);
    Boolean addInstructor(String code, String instructorName);
}