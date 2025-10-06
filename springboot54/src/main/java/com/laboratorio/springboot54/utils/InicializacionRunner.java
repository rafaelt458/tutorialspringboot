package com.laboratorio.springboot54.utils;

import com.laboratorio.springboot54.model.Permiso;
import com.laboratorio.springboot54.model.Rol;
import com.laboratorio.springboot54.model.Usuario;
import com.laboratorio.springboot54.repository.PermisoRepository;
import com.laboratorio.springboot54.repository.RolRepository;
import com.laboratorio.springboot54.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor @Slf4j
public class InicializacionRunner implements CommandLineRunner {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PermisoRepository permisoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        log.info("Inicializando la aplicación...");

        if (this.permisoRepository.count() == 0) {
            log.info("Creando los permisos");
            Permiso read = this.permisoRepository.save(new Permiso("READ"));
            Permiso write = this.permisoRepository.save(new Permiso("WRITE"));
            Permiso delete = this.permisoRepository.save(new Permiso("DELETE"));

            log.info("Creando los roles");
            Rol user = this.rolRepository.save(new Rol("USER", Set.of(read, write)));
            Rol admin = this.rolRepository.save(new Rol("ADMIN", Set.of(read, write, delete)));
            Rol guest = this.rolRepository.save(new Rol("GUEST", Set.of(read)));

            log.info("Creando los usuarios");
            this.usuarioRepository.save(
                    new Usuario("rafa", this.passwordEncoder.encode("1234"), "rafa@mail.com",
                            "Rafa", Set.of(admin))
            );
            this.usuarioRepository.save(
                    new Usuario("pedro", this.passwordEncoder.encode("1234"), "pedro@mail.com",
                            "Pedro", Set.of(user))
            );
            this.usuarioRepository.save(
                    new Usuario("juan", this.passwordEncoder.encode("1234"), "juan@mail.com",
                            "Juan", Set.of(guest))
            );
        }
    }
}