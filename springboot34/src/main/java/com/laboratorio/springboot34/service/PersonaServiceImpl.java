package com.laboratorio.springboot34.service;

import com.laboratorio.springboot34.model.dto.PersonaRequest;
import com.laboratorio.springboot34.model.dto.PersonaResponse;
import com.laboratorio.springboot34.model.entity.Persona;
import com.laboratorio.springboot34.model.mapper.PersonaRequestMapper;
import com.laboratorio.springboot34.model.mapper.PersonaResponseMapper;
import com.laboratorio.springboot34.repository.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonaServiceImpl implements PersonaService {
    private final PersonaRepository personaRepository;
    private final PersonaRequestMapper personaRequestMapper;
    private final PersonaResponseMapper personaResponseMapper;

    @Override
    public List<PersonaResponse> findAll() {
        List<Persona> personas = this.personaRepository.findAll();
        return this.personaResponseMapper.toPersonaResponseList(personas);
    }

    @Override
    public PersonaResponse createPersona(PersonaRequest request) {
        Persona persona = this.personaRequestMapper.toPersona(request);
        persona.getDatosContacto().setPersona(persona);
        Persona personaNueva = this.personaRepository.save(persona);
        return this.personaResponseMapper.toPersonaResponse(personaNueva);
    }
}