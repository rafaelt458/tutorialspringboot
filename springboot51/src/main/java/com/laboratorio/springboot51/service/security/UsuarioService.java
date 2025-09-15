package com.laboratorio.springboot51.service.security;

import com.laboratorio.springboot51.model.Usuario;

import java.util.Optional;

public interface UsuarioService {
    Optional<Usuario> findByUsername(String username);
}
