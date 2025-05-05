package com.laboratorio.springboot32.model.mapper;

import com.laboratorio.springboot32.model.dto.PersonaResponse;
import com.laboratorio.springboot32.model.entity.Persona;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonaResponseMapper {
    @Mappings(
            @Mapping(source = "id", target = "codigo")
    )
    PersonaResponse toPersonaResponse(Persona persona);

    List<PersonaResponse> toPersonaResponseList(List<Persona> personas);
}