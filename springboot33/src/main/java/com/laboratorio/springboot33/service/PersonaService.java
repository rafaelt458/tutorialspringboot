package com.laboratorio.springboot33.service;

import com.laboratorio.springboot33.model.dto.PersonaRequest;
import com.laboratorio.springboot33.model.dto.PersonaResponse;

import java.util.List;

public interface PersonaService {
    List<PersonaResponse> findAll();
    PersonaResponse createPersona(PersonaRequest request);
}