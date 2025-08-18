package com.laboratorio.springboot46.service;

import com.laboratorio.springboot46.model.Usuario;

import java.util.Optional;

public interface UsuarioService {
    Optional<Usuario> findByUsername(String username);
}
