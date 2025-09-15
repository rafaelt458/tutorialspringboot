package com.laboratorio.springboot51.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorInfo {
    private int codigo;
    private String mensaje;
    private Map<String, String> mensajes;
    private LocalDateTime hora;

    public ErrorInfo(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.mensajes = null;
        this.hora = LocalDateTime.now();
    }

    public ErrorInfo(int codigo, Map<String, String> mensajes) {
        this.codigo = codigo;
        this.mensaje = null;
        this.mensajes = mensajes;
        this.hora = LocalDateTime.now();
    }
}