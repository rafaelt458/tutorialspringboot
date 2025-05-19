package com.laboratorio.springboot34.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DatosContactoRequest {
    @NotBlank
    @Size(max = 255, message = "La dirección debe tener como máximo 255 caracteres")
    private String direccion;

    @Pattern(regexp = "^\\+\\d{1,4}\\s\\d{9}$", message = "El teléfono debe incluir el código de país separado del número por un espacio en blanco")
    private String telefono;

    @Email(message = "Formato de correo electrónico inválido")
    private String email;
}