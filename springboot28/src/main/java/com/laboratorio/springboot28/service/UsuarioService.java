package com.laboratorio.springboot28.service;

import com.laboratorio.springboot28.dto.UsuarioRequest;
import com.laboratorio.springboot28.dto.UsuarioResponse;

public interface UsuarioService {
    UsuarioResponse create(UsuarioRequest request);
}