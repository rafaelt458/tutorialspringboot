package com.laboratorio.springboot32.service;

import com.laboratorio.springboot32.model.dto.PersonaRequest;
import com.laboratorio.springboot32.model.dto.PersonaResponse;

import java.util.List;

public interface PersonaService {
    List<PersonaResponse> findAll();
    PersonaResponse createPersona(PersonaRequest request);
}