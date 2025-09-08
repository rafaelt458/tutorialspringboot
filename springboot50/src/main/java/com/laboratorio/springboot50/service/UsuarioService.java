package com.laboratorio.springboot50.service;

import com.laboratorio.springboot50.model.Usuario;

import java.util.Optional;

public interface UsuarioService {
    Optional<Usuario> findByUsername(String username);
}
