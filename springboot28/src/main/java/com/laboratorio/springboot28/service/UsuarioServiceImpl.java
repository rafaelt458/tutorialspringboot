package com.laboratorio.springboot28.service;

import com.laboratorio.springboot28.dto.UsuarioRequest;
import com.laboratorio.springboot28.dto.UsuarioResponse;
import com.laboratorio.springboot28.model.Usuario;
import com.laboratorio.springboot28.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor @Slf4j
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ParareloService parareloService;

    @Override
    @Transactional
    // @Transactional(noRollbackFor = {RuntimeException.class})
    public UsuarioResponse create(UsuarioRequest request) {
        Usuario usuario = new Usuario(request.getUsername(), request.getPassword());
        Usuario usuarioNuevo = this.usuarioRepository.save(usuario);

        // this.parareloService.funcion1(request);

        try {
            // this.parareloService.funcion2(request);
            // this.parareloService.funcion3(request);
            this.parareloService.funcion4(request);
        } catch (Exception e) {
            log.error("Excepción capturada");
        }

        return new UsuarioResponse(usuario);
    }
}