package com.laboratorio.springboot53.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UsuarioRequest {
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(max = 50, message = "El nombre de usuario debe tener como máximo 50 caracteres")
    private String username;

    @NotBlank(message = "La contraseña del usuario es obligatoria")
    @Size(max = 30, message = "La contraseña del usuario debe tener como máximo 30 caracteres")
    private String password;

    @NotBlank(message = "La dirección de correo del usuario es obligatoria")
    @Size(max = 30, message = "La dirección de correo del usuario debe tener como máximo 255 caracteres")
    @Email(message = "El formato de la dirección de correo es inválido")
    private String email;

    @NotBlank(message = "Los nombres y apellidos del usuario son obligatorios")
    @Size(max = 50, message = "Los nombres y apellidos del usuario deben tener como máximo 50 caracteres")
    private String nombre;
}