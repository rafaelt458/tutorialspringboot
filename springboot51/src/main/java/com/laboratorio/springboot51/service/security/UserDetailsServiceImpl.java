package com.laboratorio.springboot51.service.security;

import com.laboratorio.springboot51.exception.InvalidUserExcepcion;
import com.laboratorio.springboot51.model.Usuario;
import com.laboratorio.springboot51.security.data.LoginInfo;
import com.laboratorio.springboot51.security.data.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilsService jwtUtilsService;

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

    public TokenInfo login(LoginInfo loginInfo) throws UsernameNotFoundException, InvalidUserExcepcion {
        String username = loginInfo.getUsername();
        String password = loginInfo.getPassword();

        UserDetails userDetails = this.loadUserByUsername(username);
        if (!userDetails.isEnabled()) {
            throw new InvalidUserExcepcion("El usuario está deshabilitado");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new InvalidUserExcepcion("Las credenciales suministradas son incorrectas");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new TokenInfo(this.jwtUtilsService.createToken(authentication));
    }
}