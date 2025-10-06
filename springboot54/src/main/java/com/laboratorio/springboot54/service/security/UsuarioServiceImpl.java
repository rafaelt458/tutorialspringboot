package com.laboratorio.springboot54.service.security;

import com.laboratorio.springboot54.dto.UsuarioRequest;
import com.laboratorio.springboot54.exception.DatabaseException;
import com.laboratorio.springboot54.exception.InvalidOperationException;
import com.laboratorio.springboot54.exception.ResourceNotFoundException;
import com.laboratorio.springboot54.model.Rol;
import com.laboratorio.springboot54.model.Usuario;
import com.laboratorio.springboot54.repository.RolRepository;
import com.laboratorio.springboot54.repository.UsuarioRepository;
import com.laboratorio.springboot54.security.data.NewPasswordInfo;
import com.laboratorio.springboot54.service.SendEmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
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
    public void signUp(UsuarioRequest request) {
        Optional<Usuario> usuarioBD = this.usuarioRepository.findOneByUsername(request.getUsername());
        if (usuarioBD.isPresent()) {
            throw new InvalidOperationException("Ya existe un usuario con el mismo nombre de usuario");
        }

        usuarioBD = this.usuarioRepository.findOneByEmail(request.getEmail());
        if (usuarioBD.isPresent()) {
            throw new InvalidOperationException("Ya existe un usuario con la misma dirección de correo");
        }

        Usuario usuarioNuevo;

        try {
            Optional<Rol> rolBD = this.rolRepository.findOneByNombre("USER");
            if (rolBD.isEmpty()) {
                throw new InvalidOperationException("Error de configuración. No existe el Rol USER.");
            }

            String password = this.passwordEncoder.encode(request.getPassword());
            Usuario usuario = new Usuario(request, password, rolBD.get());
            usuarioNuevo = this.usuarioRepository.save(usuario);
        } catch (InvalidOperationException e) {
            throw e;
        } catch (Exception e) {
            String message = String.format("Error de base de datos creando el usuario: %s", request.getUsername());
            throw new DatabaseException(message, e);
        }

        String validateData = String.format("%d-%s", usuarioNuevo.getId(), usuarioNuevo.getUsername());
        String codedData = new String(Base64.getEncoder().encode(validateData.getBytes()));
        String enlace = "http://localhost:8090/api/usuarios/validate/" + codedData;

        String texto = String.format("""
                Hola %s,
                
                Su usuario ha sido creado con las siguientes con el  nombre de usuario: %s.
                Para activar su usuario, debe validar su dirección de correo electrónico haciendo clic en el enlace: %s
                
                Por seguridad, debe cambiar su contraseña a la brevedad posible.
                """, request.getNombre(), request.getUsername(), enlace);

        this.sendEmailService.sendEmail(request.getEmail(), "Su usuario ha sido creado", texto);
    }

    @Override
    public void validateUser(String validateData) {
        String decodedData = new String(Base64.getDecoder().decode(validateData.getBytes()));
        String[] parts = decodedData.split("-");
        if (parts.length != 2) {
            throw new InvalidOperationException("No se puede validar la dirección de correo electrónico");
        }

        int usuarioId = Integer.parseInt(parts[0]);
        String username = parts[1];

        try {
            Optional<Usuario> usuarioDB = this.usuarioRepository.findById(usuarioId);
            if (usuarioDB.isEmpty()) {
                throw new ResourceNotFoundException("Imposible validar la dirección de correo de un usuario inexistente");
            }
            Usuario usuario = usuarioDB.get();
            if (!usuario.getUsername().equals(username)) {
                throw new InvalidOperationException("No se puede validar la dirección de correo por inconsistencia en los datos");
            }

            usuario.setVerificado(true);
            this.usuarioRepository.save(usuario);
        } catch (Exception e) {
            String message = String.format("Error de base de datos validando el email del usuario: %s", username);
            throw new DatabaseException(message, e);
        }
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