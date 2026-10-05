package com.laboratorio.matricula_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
		scanBasePackages = {
				"com.laboratorio.framework",
				"com.laboratorio.matricula_service"
		}
)
@EnableFeignClients
public class MatriculaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatriculaServiceApplication.class, args);
	}

}