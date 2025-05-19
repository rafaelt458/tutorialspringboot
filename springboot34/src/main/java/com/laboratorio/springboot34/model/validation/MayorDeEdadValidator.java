package com.laboratorio.springboot34.model.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class MayorDeEdadValidator implements ConstraintValidator<MayorDeEdad, String> {
    @Override
    public boolean isValid(String fechaStr, ConstraintValidatorContext constraintValidatorContext) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNacimiento;
        try {
            fechaNacimiento = LocalDate.parse(fechaStr, formatter);
        } catch (DateTimeParseException e) {
            return false;
        }

        long edad = ChronoUnit.YEARS.between(fechaNacimiento, LocalDate.now());

        return edad >= 18;
    }
}