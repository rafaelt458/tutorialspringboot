package com.laboratorio.springboot34.model.validation;

import com.laboratorio.springboot34.model.dto.PersonaRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class ExperienciaValidator implements ConstraintValidator<Experiencia, PersonaRequest> {
    @Override
    public boolean isValid(PersonaRequest personaRequest, ConstraintValidatorContext constraintValidatorContext) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNacimiento;
        try {
            fechaNacimiento = LocalDate.parse(personaRequest.getFechaNacimiento(), formatter);
        } catch (DateTimeParseException e) {
            return false;
        }

        long edad = ChronoUnit.YEARS.between(fechaNacimiento, LocalDate.now());

        return edad >= 18 + personaRequest.getExperiencia();
    }
}