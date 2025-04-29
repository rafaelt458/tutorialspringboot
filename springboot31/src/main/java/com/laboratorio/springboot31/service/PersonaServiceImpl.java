package com.laboratorio.springboot31.service;

import com.laboratorio.springboot31.dto.PersonaRequest;
import com.laboratorio.springboot31.dto.PersonaResponse;
import com.laboratorio.springboot31.model.Persona;
import com.laboratorio.springboot31.repository.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonaServiceImpl implements PersonaService {
    private final PersonaRepository personaRepository;

    @Override
    public PersonaResponse createPersona(PersonaRequest request) {
        Persona persona = new Persona(request);
        Persona personaNueva = this.personaRepository.save(persona);
        return new PersonaResponse(personaNueva);
    }
}