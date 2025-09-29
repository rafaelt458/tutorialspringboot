package com.laboratorio.springboot53.security.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class EmailInfo {
    @NotBlank(message = "La dirección de envío no puede ser nula")
    @Email(message = "Dirección de correo electrónico inválida")
    private String to;

    @NotBlank(message = "El asunto de email no puede ser nulo")
    private String subject;

    @NotBlank(message = "El contenido de email no puede ser nulo")
    private String text;
}