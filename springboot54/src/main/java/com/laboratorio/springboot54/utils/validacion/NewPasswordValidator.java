package com.laboratorio.springboot54.utils.validacion;

import com.laboratorio.springboot54.security.data.NewPasswordInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NewPasswordValidator implements ConstraintValidator<ValidNewPassword, NewPasswordInfo> {
    @Override
    public boolean isValid(NewPasswordInfo newPasswordInfo, ConstraintValidatorContext constraintValidatorContext) {
        if (newPasswordInfo == null) {
            return false;
        }
        return newPasswordInfo.getNewPassword().equals(newPasswordInfo.getConfirmation());
    }
}
