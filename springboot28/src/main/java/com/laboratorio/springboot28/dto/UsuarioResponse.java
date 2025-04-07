package com.laboratorio.springboot28.dto;

import com.laboratorio.springboot28.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class UsuarioResponse {
    private Integer id;
    private String username;
    private boolean activo;

    public UsuarioResponse(Usuario usuario) {
        this.id = usuario.getId();
        this.username = usuario.getUsername();
        this.activo = usuario.isActivo();
    }
}