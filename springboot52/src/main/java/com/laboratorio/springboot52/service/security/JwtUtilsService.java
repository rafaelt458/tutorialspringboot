package com.laboratorio.springboot52.service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JwtUtilsService {
    @Value("${com.laboratorio.jwt.secret-word}")
    private String privateKey;

    @Value("${com.laboratorio.jwt.expiration-milliseconds}")
    private int expirationMillis;

    @Value("${com.laboratorio.jwt.user-generator}")
    private String userGenerator;

    public String createToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
        String username = authentication.getPrincipal().toString();
        String authorities = authentication.getAuthorities().stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.joining(","));

        return JWT.create()
                .withIssuer(this.userGenerator)
                .withSubject(username)
                .withClaim("authorities", authorities)
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusMillis(this.expirationMillis))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(Instant.now())
                .sign(algorithm);
    }

    public DecodedJWT validateToken(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(this.userGenerator)
                .build();

        return verifier.verify(token);
    }

    public String extractUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }

    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);
    }
}