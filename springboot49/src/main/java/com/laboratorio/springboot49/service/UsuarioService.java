package com.laboratorio.springboot49.service;

import com.laboratorio.springboot49.model.Usuario;

import java.util.Optional;

public interface UsuarioService {
    Optional<Usuario> findByUsername(String username);
}
