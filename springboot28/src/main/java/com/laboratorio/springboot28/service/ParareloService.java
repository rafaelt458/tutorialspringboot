package com.laboratorio.springboot28.service;

import com.laboratorio.springboot28.dto.UsuarioRequest;

import java.io.IOException;

public interface ParareloService {
    void funcion1(UsuarioRequest request);
    void funcion2(UsuarioRequest request);
    void funcion3(UsuarioRequest request) throws IOException;
    void funcion4(UsuarioRequest request) throws IOException;
}