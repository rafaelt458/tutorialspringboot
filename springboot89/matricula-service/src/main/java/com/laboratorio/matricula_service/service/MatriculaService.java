package com.laboratorio.matricula_service.service;

import com.laboratorio.framework.model.event.CupoCursoRechazadoEvent;
import com.laboratorio.framework.model.event.CupoCursoReservadoEvent;
import com.laboratorio.matricula_service.model.dto.MatriculaRequest;
import com.laboratorio.matricula_service.model.dto.MatriculaResponse;

import java.util.List;

public interface MatriculaService {
    List<MatriculaResponse> getMatriculasByCurso(String codigoCurso);
    List<MatriculaResponse> getMatriculasByEstado(String codigoCurso, String estado);
    MatriculaResponse inscribirEstudiante(MatriculaRequest request);
    void confirmarMatricula(CupoCursoReservadoEvent event);
    void cancelarMatricula(CupoCursoRechazadoEvent event);
}