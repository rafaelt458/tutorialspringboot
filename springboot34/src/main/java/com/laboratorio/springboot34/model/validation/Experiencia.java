package com.laboratorio.springboot34.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExperienciaValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Experiencia {
    String message() default "La experiencia no es coherente con la edad de la persona";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}