package com.laboratorio.springboot54.controller;

import com.laboratorio.springboot54.dto.UsuarioRequest;
import com.laboratorio.springboot54.security.data.LoginInfo;
import com.laboratorio.springboot54.security.data.NewPasswordInfo;
import com.laboratorio.springboot54.security.data.TokenInfo;
import com.laboratorio.springboot54.service.security.UserDetailsServiceImpl;
import com.laboratorio.springboot54.service.security.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UserDetailsServiceImpl userDetailsService;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<TokenInfo> login(@Valid @RequestBody LoginInfo loginInfo) {
        return ResponseEntity.ok(this.userDetailsService.login(loginInfo));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody UsuarioRequest request) {
        this.usuarioService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("El usuario se ha creado correctamente");
    }

    @GetMapping("/validate/{validateData}")
    public ResponseEntity<String> validateUser(@PathVariable String validateData) {
        this.usuarioService.validateUser(validateData);
        return ResponseEntity.ok("La dirección de correo electrónico del usuario ha sido validada");
    }

    @PatchMapping("/block/{username}")
    public ResponseEntity<String> blockUser(@PathVariable String username) {
        this.usuarioService.blockUser(username);
        return ResponseEntity.ok("El usuario ha sido bloqueado correctamente");
    }

    @PutMapping("/newpassword")
    public ResponseEntity<String> changePassword(@Valid @RequestBody NewPasswordInfo info) {
        this.usuarioService.changePassword(info);
        return ResponseEntity.ok("La clave se ha cambiado correctamente");
    }
}