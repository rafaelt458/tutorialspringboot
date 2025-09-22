package com.laboratorio.springboot52.service.security;

import com.laboratorio.springboot52.model.Usuario;
import com.laboratorio.springboot52.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return this.usuarioRepository.findOneByUsername(username);
    }
}