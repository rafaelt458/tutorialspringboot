package com.laboratorio.springboot53.security.data;

import com.laboratorio.springboot53.utils.validacion.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@ValidPassword
public class NewPasswordInfo {
    @NotBlank(message = "La contraseña actual del usuario es obligatoria")
    private String password;

    @NotBlank(message = "La contraseña nueva del usuario es obligatoria")
    private String newPassword;
    private String confirmation;
}