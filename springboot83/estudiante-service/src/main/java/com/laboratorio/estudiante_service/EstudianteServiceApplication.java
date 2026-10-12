package com.laboratorio.estudiante_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
		scanBasePackages = {
				"com.laboratorio.framework",
				"com.laboratorio.estudiante_service"
		}
)
public class EstudianteServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstudianteServiceApplication.class, args);
	}

}
