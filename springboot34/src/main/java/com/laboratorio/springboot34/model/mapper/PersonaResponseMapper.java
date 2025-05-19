package com.laboratorio.springboot34.model.mapper;

import com.laboratorio.springboot34.model.dto.PersonaResponse;
import com.laboratorio.springboot34.model.entity.Persona;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonaResponseMapper {
    @Mappings({
            @Mapping(source = "id", target = "codigo"),
            @Mapping(source = "datosContacto", target = "datosContactoResponse")
    })
    PersonaResponse toPersonaResponse(Persona persona);

    List<PersonaResponse> toPersonaResponseList(List<Persona> personas);
}