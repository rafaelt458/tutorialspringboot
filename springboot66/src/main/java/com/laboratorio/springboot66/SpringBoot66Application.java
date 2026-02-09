package com.laboratorio.springboot66;

import org.springframework.ai.vectorstore.pgvector.autoconfigure.PgVectorStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
		exclude = {
				PgVectorStoreAutoConfiguration.class
		}
)
public class SpringBoot66Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot66Application.class, args);
	}

}
