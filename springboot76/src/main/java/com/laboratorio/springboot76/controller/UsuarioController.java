package com.laboratorio.springboot76.controller;

import com.laboratorio.springboot76.security.data.EmailInfo;
import com.laboratorio.springboot76.security.data.LoginInfo;
import com.laboratorio.springboot76.security.data.TokenInfo;
import com.laboratorio.springboot76.service.SendEmailService;
import com.laboratorio.springboot76.service.security.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UserDetailsServiceImpl userDetailsService;
    private final SendEmailService sendEmailService;

    @PostMapping("/login")
    public ResponseEntity<TokenInfo> login(@Valid @RequestBody LoginInfo loginInfo) {
        return ResponseEntity.ok(this.userDetailsService.login(loginInfo));
    }

    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(@Valid @RequestBody EmailInfo emailInfo) {
        this.sendEmailService.sendEmail(emailInfo.getTo(), emailInfo.getSubject(), emailInfo.getText());
        return ResponseEntity.ok("Email enviado correctamente");
    }
}
