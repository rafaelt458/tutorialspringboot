package com.laboratorio.springboot66.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class VectorStoreConfig {
    @Bean
    public VectorStore getVectorStore(JdbcTemplate jdbcTemplate, @Qualifier("ollamaEmbeddingModel") EmbeddingModel model) {
        return PgVectorStore.builder(jdbcTemplate, model)
                .vectorTableName("conceptos_ia")
                .initializeSchema(true)
                .indexType(PgVectorStore.PgIndexType.HNSW)
                .distanceType(PgVectorStore.PgDistanceType.COSINE_DISTANCE)
                .dimensions(1024)
                .build();
    }
}