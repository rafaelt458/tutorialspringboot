package com.laboratorio.springboot57;

import com.laboratorio.springboot57.chutes.config.ChutesProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ChutesProperties.class)
public class SpringBoot57Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot57Application.class, args);
	}

}