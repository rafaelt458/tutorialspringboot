package com.laboratorio.springboot54.security.data;

import com.laboratorio.springboot54.utils.validacion.ValidNewPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@ValidNewPassword
public class NewPasswordInfo {
    @NotBlank(message = "La contraseña actual del usuario es obligatoria")
    private String password;

    @NotBlank(message = "La contraseña nueva del usuario es obligatoria")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,30}$",
            message = "La contraseña debe tener entre 8 y 30 caracteres, incluyendo al menos una minúscula, una mayúscula, un número y un símbolo"
    )
    private String newPassword;

    private String confirmation;
}