package com.laboratorio.springboot53.security;

import com.laboratorio.springboot53.exception.CustomAccessDeniedHandler;
import com.laboratorio.springboot53.exception.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   JwtRequestFilter jwtRequestFilter,
                                                   CustomAuthenticationEntryPoint entryPoint,
                                                   CustomAccessDeniedHandler deniedHandler) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(http -> {
                    // Endpoints públicos
                    http.requestMatchers("/api/usuarios/login").permitAll();
                    // Endpoints privados
                    http.requestMatchers("/api/usuarios/create").hasRole("ADMIN");
                    http.requestMatchers("/api/usuarios/block/**").hasRole("ADMIN");
                    http.requestMatchers("/api/usuarios/newpassword").hasAuthority("WRITE");
                    http.requestMatchers(HttpMethod.GET).hasAuthority("READ");
                    http.requestMatchers(HttpMethod.POST, "/api/categorias/**").hasAuthority("WRITE");
                    http.requestMatchers(HttpMethod.POST, "/api/productos/**").hasAuthority("WRITE");
                    http.requestMatchers(HttpMethod.PUT, "/api/categorias/**").hasAuthority("WRITE");
                    http.requestMatchers(HttpMethod.PUT, "/api/productos/**").hasAuthority("WRITE");
                    http.requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN");
                    // Endpoints no definidos
                    http.anyRequest().authenticated();
                })
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(entryPoint)
                        .accessDeniedHandler(deniedHandler)
                )
                .httpBasic(auth -> auth.disable())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of(CorsConfiguration.ALL));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}