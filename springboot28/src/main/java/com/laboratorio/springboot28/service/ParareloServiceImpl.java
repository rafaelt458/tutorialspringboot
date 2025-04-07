package com.laboratorio.springboot28.service;

import com.laboratorio.springboot28.dto.UsuarioRequest;
import com.laboratorio.springboot28.model.Usuario;
import com.laboratorio.springboot28.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor @Slf4j
public class ParareloServiceImpl implements ParareloService {
    private final UsuarioRepository usuarioRepository;

    @Override
    public void funcion1(UsuarioRequest request) {
        Usuario usuario = new Usuario(request.getUsername() + " BIS", request.getPassword());
        Usuario usuarioNuevo = this.usuarioRepository.save(usuario);

        throw new RuntimeException();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void funcion2(UsuarioRequest request) {
        Usuario usuario = new Usuario(request.getUsername() + " BIS", request.getPassword());
        Usuario usuarioNuevo = this.usuarioRepository.save(usuario);

        throw new RuntimeException();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void funcion3(UsuarioRequest request) throws IOException {
        Usuario usuario = new Usuario(request.getUsername() + " BIS", request.getPassword());
        Usuario usuarioNuevo = this.usuarioRepository.save(usuario);

        throw new IOException();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {IOException.class})
    public void funcion4(UsuarioRequest request) throws IOException {
        Usuario usuario = new Usuario(request.getUsername() + " BIS", request.getPassword());
        Usuario usuarioNuevo = this.usuarioRepository.save(usuario);

        throw new IOException();
    }
}