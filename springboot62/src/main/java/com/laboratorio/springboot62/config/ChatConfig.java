package com.laboratorio.springboot62.config;

import com.laboratorio.springboot62.memoryimpl.LabJdbcChatMemoryRepository;
import com.laboratorio.springboot62.memoryimpl.LabPostgresChatMemoryRepositoryDialect;
import com.laboratorio.springboot62.memoryimpl.TimeWindowChatMemory;
import com.laboratorio.springboot62.memoryimpl.TokenWindowChatMemory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.ai.mistralai.api.MistralAiApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

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
    public ChatMemoryRepository chatMemoryRepository(JdbcTemplate jdbcTemplate) {
        return LabJdbcChatMemoryRepository.builder()
                .jdbcTemplate(jdbcTemplate)
                .dialect(new LabPostgresChatMemoryRepositoryDialect())
                .build();
    }

    @Bean(name = "timeWindowChatMemory")
    public ChatMemory timeWindowChatMemory(ChatMemoryRepository repository) {
        return TimeWindowChatMemory.builder()
                .chatMemoryRepository(repository)
                .maxMessages(20)
                .maxAge(Duration.of(48, ChronoUnit.HOURS))
                .build();
    }

    @Bean(name = "tokenWindowChatMemory")
    public ChatMemory tokenWindowChatMemory(ChatMemoryRepository repository) {
        return TokenWindowChatMemory.builder()
                .chatMemoryRepository(repository)
                .maxMessages(20)
                .maxTokens(4096)
                .build();
    }

    @Bean(name = "timeLimitedChatClient")
    public ChatClient timeLimitedChatClient(ChatModel chatModel, @Qualifier("timeWindowChatMemory")ChatMemory chatMemory) {
        return ChatClient.builder(chatModel)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    @Bean(name = "tokenLimitedChatClient")
    public ChatClient tokenLimitedChatClient(ChatModel chatModel, @Qualifier("tokenWindowChatMemory")ChatMemory chatMemory) {
        return ChatClient.builder(chatModel)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }
}