package com.laboratorio.springboot53.service.security;

import com.laboratorio.springboot53.dto.UsuarioRequest;
import com.laboratorio.springboot53.exception.DatabaseException;
import com.laboratorio.springboot53.exception.InvalidOperationException;
import com.laboratorio.springboot53.exception.ResourceNotFoundException;
import com.laboratorio.springboot53.model.Rol;
import com.laboratorio.springboot53.model.Usuario;
import com.laboratorio.springboot53.repository.RolRepository;
import com.laboratorio.springboot53.repository.UsuarioRepository;
import com.laboratorio.springboot53.security.data.NewPasswordInfo;
import com.laboratorio.springboot53.service.SendEmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final SendEmailService sendEmailService;

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return this.usuarioRepository.findOneByUsername(username);
    }

    @Override
    @Transactional
    public void createUser(UsuarioRequest request) {
        Optional<Usuario> usuarioBD = this.usuarioRepository.findOneByUsername(request.getUsername());
        if (usuarioBD.isPresent()) {
            throw new InvalidOperationException("Ya existe un usuario con el mismo nombre de usuario");
        }

        usuarioBD = this.usuarioRepository.findOneByEmail(request.getEmail());
        if (usuarioBD.isPresent()) {
            throw new InvalidOperationException("Ya existe un usuario con la misma dirección de correo");
        }

        try {
            Optional<Rol> rolBD = this.rolRepository.findOneByNombre("USER");
            if (rolBD.isEmpty()) {
                throw new InvalidOperationException("Error de configuración. No existe el Rol USER.");
            }

            String password = this.passwordEncoder.encode(request.getPassword());
            Usuario usuario = new Usuario(request, password, rolBD.get());
            this.usuarioRepository.save(usuario);
        } catch (InvalidOperationException e) {
            throw e;
        } catch (Exception e) {
            String message = String.format("Error de base de datos creando el usuario: %s", request.getUsername());
            throw new DatabaseException(message, e);
        }

        String texto = String.format("""
                Hola %s,
                
                Su usuario ha sido creado con las siguientes credenciales:
                Nombre de usuario: %s
                Contraseña: %s
                
                Por seguridad, debe cambiar su contraseña a la brevedad posible.
                """, request.getNombre(), request.getUsername(), request.getPassword());

        this.sendEmailService.sendEmail(request.getEmail(), "Su usuario ha sido creado", texto);
    }

    @Override
    @Transactional
    public void blockUser(String username) {
        Optional<Usuario> usuarioBD = this.usuarioRepository.findOneByUsername(username);
        if (usuarioBD.isEmpty()) {
            throw new ResourceNotFoundException("No se puede bloquear el usuario porque no existe");
        }

        try {
            Usuario usuario = usuarioBD.get();
            usuario.setActivo(false);
            this.usuarioRepository.save(usuario);
        } catch (Exception e) {
            String message = String.format("Error de base de datos bloqueando el usuario: %s", username);
            throw new DatabaseException(message, e);
        }
    }

    @Override
    @Transactional
    public void changePassword(NewPasswordInfo info) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        try {
            Optional<Usuario> usuarioBD = this.usuarioRepository.findOneByUsername(username);
            if (usuarioBD.isEmpty()) {
                throw new ResourceNotFoundException("No se ha podido recuperar los datos del usuario " + username);
            }

            Usuario usuario = usuarioBD.get();
            if (!this.passwordEncoder.matches(info.getPassword(), usuario.getPassword())) {
                throw new InvalidOperationException("El password actual no es correcto");
            }

            String password = this.passwordEncoder.encode(info.getNewPassword());
            usuario.setPassword(password);
            this.usuarioRepository.save(usuario);
        } catch (ResourceNotFoundException | InvalidOperationException e) {
            throw e;
        } catch (Exception e) {
            String message = String.format("Error de base de datos cambiando la contraseña del usuario: %s", username);
            throw new DatabaseException(message, e);
        }
    }
}