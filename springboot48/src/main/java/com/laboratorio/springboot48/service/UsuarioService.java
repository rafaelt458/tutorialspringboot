package com.laboratorio.springboot48.service;

import com.laboratorio.springboot48.model.Usuario;

import java.util.Optional;

public interface UsuarioService {
    Optional<Usuario> findByUsername(String username);
}
