package com.laboratorio.springboot55.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {
    @Bean
    public ChatModel chatModel() {
        return OllamaChatModel.builder()
                .ollamaApi(new OllamaApi.Builder().baseUrl("http://192.168.1.137:11434").build())
                .defaultOptions(
                        OllamaOptions.builder()
                                .model("gpt-oss:20b")
                                .temperature(0.7)
                                .topK(40)
                                .topP(0.9)
                                .build()
                ).build();
    }

    @Bean
    public ChatClient chatClient(ChatModel chatModel) {
        return ChatClient.create(chatModel);
    }
}