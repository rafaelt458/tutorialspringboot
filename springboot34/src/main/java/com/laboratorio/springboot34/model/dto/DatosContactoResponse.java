package com.laboratorio.springboot34.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DatosContactoResponse {
    private String direccion;
    private String telefono;
    private String email;
}