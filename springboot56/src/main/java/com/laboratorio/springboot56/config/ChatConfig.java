package com.laboratorio.springboot56.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.ai.mistralai.api.MistralAiApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public ChatClient chatClient(ChatModel chatModel) {
        return ChatClient.create(chatModel);
    }
}