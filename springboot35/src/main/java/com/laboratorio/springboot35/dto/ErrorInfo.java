package com.laboratorio.springboot35.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ErrorInfo {
    private int codigo;
    private String mensaje;
    private LocalDateTime hora;

    public ErrorInfo(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.hora = LocalDateTime.now();
    }
}