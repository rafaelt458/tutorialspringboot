package com.laboratorio.springboot54.utils.validacion;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NewPasswordValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidNewPassword {
    String message() default "La clave y su confirmación no coinciden";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}