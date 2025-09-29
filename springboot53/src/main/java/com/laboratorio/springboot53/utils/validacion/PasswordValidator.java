package com.laboratorio.springboot53.utils.validacion;

import com.laboratorio.springboot53.security.data.NewPasswordInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, NewPasswordInfo> {
    @Override
    public boolean isValid(NewPasswordInfo newPasswordInfo, ConstraintValidatorContext constraintValidatorContext) {
        if (newPasswordInfo == null) {
            return false;
        }
        return newPasswordInfo.getNewPassword().equals(newPasswordInfo.getConfirmation());
    }
}
