package com.laboratorio.springboot53.repository;

import com.laboratorio.springboot53.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findOneByUsername(String username);
    Optional<Usuario> findOneByEmail(String email);
}