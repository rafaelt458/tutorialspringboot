package com.laboratorio.springboot28.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class UsuarioRequest {
    private String username;
    private String password;
}