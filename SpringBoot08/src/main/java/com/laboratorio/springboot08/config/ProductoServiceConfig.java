package com.laboratorio.springboot08.config;

import com.laboratorio.springboot08.service.ProductoService;
import com.laboratorio.springboot08.service.ProductoServiceImpl1;
import com.laboratorio.springboot08.service.ProductoServiceImpl2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductoServiceConfig {
/*    @Bean
    public ProductoService productoService() {
        // Lógica que permite decidir el bean a inyectar
        double aleatorio = Math.random();
        if (aleatorio <= 0.5) {
            System.out.println("Se inyecta la implementación 1");
            return new ProductoServiceImpl1();
        } else {
            System.out.println("Se inyecta la implementación 2");
            return new ProductoServiceImpl2();
        }
    } */
}