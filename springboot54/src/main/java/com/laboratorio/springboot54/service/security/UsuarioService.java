package com.laboratorio.springboot54.service.security;

import com.laboratorio.springboot54.dto.UsuarioRequest;
import com.laboratorio.springboot54.model.Usuario;
import com.laboratorio.springboot54.security.data.NewPasswordInfo;

import java.util.Optional;

public interface UsuarioService {
    Optional<Usuario> findByUsername(String username);
    void signUp(UsuarioRequest request);
    void validateUser(String validateData);
    void blockUser(String username);
    void changePassword(NewPasswordInfo info);
}