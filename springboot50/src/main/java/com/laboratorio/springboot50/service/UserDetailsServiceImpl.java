package com.laboratorio.springboot50.service;

import com.laboratorio.springboot50.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioDB = this.usuarioService.findByUsername(username);
        if (usuarioDB.isEmpty()) {
            throw new UsernameNotFoundException("No existe el usuario " + username);
        }

        Usuario usuario = usuarioDB.get();
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        usuario.getRoles()
                .forEach(rol -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(rol.getNombre()))));

        usuario.getRoles().stream()
                .flatMap(rol -> rol.getPermisos().stream())
                .forEach(permiso -> authorityList.add(new SimpleGrantedAuthority(permiso.getNombre())));

        return User.withUsername(username)
                .password(usuario.getPassword())
                .disabled(!usuario.isActivo())
                .authorities(authorityList)
                .build();
    }
}