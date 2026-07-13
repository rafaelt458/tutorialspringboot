package com.laboratorio.springboot76.service.security;

import com.laboratorio.springboot76.model.Usuario;

import java.util.Optional;

public interface UsuarioService {
    Optional<Usuario> findByUsername(String username);
}
