package com.laboratorio.springboot31.service;

import com.laboratorio.springboot31.dto.PersonaRequest;
import com.laboratorio.springboot31.dto.PersonaResponse;

public interface PersonaService {
    PersonaResponse createPersona(PersonaRequest request);
}