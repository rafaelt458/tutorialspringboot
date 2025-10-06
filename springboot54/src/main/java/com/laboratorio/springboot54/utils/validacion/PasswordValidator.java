package com.laboratorio.springboot54.utils.validacion;

import com.laboratorio.springboot54.dto.UsuarioRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, UsuarioRequest> {
    @Override
    public boolean isValid(UsuarioRequest usuarioRequest, ConstraintValidatorContext constraintValidatorContext) {
        if (usuarioRequest == null) {
            return false;
        }

        return usuarioRequest.getPassword().equals(usuarioRequest.getConfirmacion());
    }
}
