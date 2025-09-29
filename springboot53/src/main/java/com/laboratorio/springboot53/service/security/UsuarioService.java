package com.laboratorio.springboot53.service.security;

import com.laboratorio.springboot53.dto.UsuarioRequest;
import com.laboratorio.springboot53.model.Usuario;
import com.laboratorio.springboot53.security.data.NewPasswordInfo;

import java.util.Optional;

public interface UsuarioService {
    Optional<Usuario> findByUsername(String username);
    void createUser(UsuarioRequest request);
    void blockUser(String username);
    void changePassword(NewPasswordInfo info);
}