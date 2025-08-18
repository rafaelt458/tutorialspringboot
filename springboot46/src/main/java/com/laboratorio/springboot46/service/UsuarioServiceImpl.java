package com.laboratorio.springboot46.service;

import com.laboratorio.springboot46.model.Usuario;
import com.laboratorio.springboot46.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    /* private final PasswordEncoder passwordEncoder;
    private List<Usuario> usuarios;

    @PostConstruct
    public void inicializarUsuarios() {
        this.usuarios = List.of(
                new Usuario("rafa", this.passwordEncoder.encode("1234"), "rafa@mail.com",
                        "Rafa", true, LocalDateTime.now())
        );
    } */

    private final UsuarioRepository usuarioRepository;

    @Override
    public Optional<Usuario> findByUsername(String username) {
        /* return this.usuarios.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst(); */

        return this.usuarioRepository.findOneByUsername(username);
    }
}