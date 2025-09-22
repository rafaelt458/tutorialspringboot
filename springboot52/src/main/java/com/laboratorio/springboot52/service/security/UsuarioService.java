package com.laboratorio.springboot52.service.security;

import com.laboratorio.springboot52.model.Usuario;

import java.util.Optional;

public interface UsuarioService {
    Optional<Usuario> findByUsername(String username);
}
