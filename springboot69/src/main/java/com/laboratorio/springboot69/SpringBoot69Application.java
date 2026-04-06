package com.laboratorio.springboot69;

import org.springframework.ai.model.stabilityai.autoconfigure.StabilityAiImageAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
		exclude = {StabilityAiImageAutoConfiguration.class}
)
public class SpringBoot69Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot69Application.class, args);
	}

}
