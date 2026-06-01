package com.laboratorio.springboot73.configuration;

import com.laboratorio.springboot73.model.dto.EmpleadoRequest;
import com.laboratorio.springboot73.model.entity.Empleado;
import com.laboratorio.springboot73.repository.EmpleadoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
// @Profile("dev")
public class DataInitializer {
    @Bean
    @Profile("dev")
    public CommandLineRunner initDataDev(EmpleadoRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new Empleado(new EmpleadoRequest("Ana", "López", "Ventas", 30)));
                repo.save(new Empleado(new EmpleadoRequest("Luis", "Martín", "IT", 28)));
            }
        };
    }

    @Bean
    @Profile("preprod")
    public CommandLineRunner initDataPreprod(EmpleadoRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new Empleado(new EmpleadoRequest("Ana", "López", "Ventas", 30)));
                repo.save(new Empleado(new EmpleadoRequest("Luis", "Martín", "IT", 28)));
                repo.save(new Empleado(new EmpleadoRequest("Marta", "Gómez", "RRHH", 35)));
            }
        };
    }
}