package com.laboratorio.springboot34.model.mapper;

import com.laboratorio.springboot34.model.dto.PersonaRequest;
import com.laboratorio.springboot34.model.entity.Persona;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersonaRequestMapper {
    @Mapping(target = "fechaNacimiento", dateFormat = "dd/MM/yyyy")
    Persona toPersona(PersonaRequest request);
}