package com.laboratorio.springboot63.config;

import org.neo4j.driver.Driver;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.neo4j.Neo4jChatMemoryRepository;
import org.springframework.ai.chat.memory.repository.neo4j.Neo4jChatMemoryRepositoryConfig;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.ai.mistralai.api.MistralAiApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ChatConfig {
    @Bean
    public ChatModel chatModel() {
        String apiKey = System.getenv("MISTRALAI_API_KEY");

        return MistralAiChatModel.builder()
                .mistralAiApi(new MistralAiApi(apiKey))
                .defaultOptions(
                        MistralAiChatOptions.builder()
                                .model(MistralAiApi.ChatModel.SMALL.getValue())
                                .temperature(0.7)
                                .topP(0.9)
                                .build()
                )
                .build();
    }

    @Bean
    @Primary
    public ChatMemoryRepository chatMemoryRepository(Driver driver) {
        Neo4jChatMemoryRepositoryConfig config = Neo4jChatMemoryRepositoryConfig.builder()
                .withDriver(driver)
                .withSessionLabel("SessionSpringAI")
                .withMessageLabel("MessageSpringAI")
                .withMetadataLabel("MetadataSpringAI")
                .withMediaLabel("Media")
                .withToolCallLabel("ToolCall")
                .withToolResponseLabel("ToolResponse")
                .build();
        return new Neo4jChatMemoryRepository(config);
    }

    @Bean
    public ChatMemory chatMemory(ChatMemoryRepository repository) {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(repository)
                .maxMessages(20)
                .build();
    }

    @Bean
    public ChatClient chatClient(ChatModel chatModel, ChatMemory chatMemory) {
        return ChatClient.builder(chatModel)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }
}