package com.laboratorio.springboot34.service;

import com.laboratorio.springboot34.model.dto.PersonaRequest;
import com.laboratorio.springboot34.model.dto.PersonaResponse;

import java.util.List;

public interface PersonaService {
    List<PersonaResponse> findAll();
    PersonaResponse createPersona(PersonaRequest request);
}