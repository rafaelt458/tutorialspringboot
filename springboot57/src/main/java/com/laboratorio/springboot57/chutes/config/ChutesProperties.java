package com.laboratorio.springboot57.chutes.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "chutes.ai.llm")
@Getter @Setter
public class ChutesProperties {
    private String apiKey;
    private String baseUrl;
    private String uri;
    private String model;
    private int maxTokens;
    private double temperature;
    private int n;

    public ChutesProperties(String apiKey, String baseUrl, String uri, String model, Integer maxTokens,
                            Double temperature, Integer n) {
        this.apiKey = apiKey;
        if (apiKey == null || apiKey.isEmpty()) {
            this.apiKey = System.getenv("CHUTES_API_KEY");
        }
        this.baseUrl = baseUrl != null ? baseUrl : "https://llm.chutes.ai";
        this.uri = uri != null ? uri : "/v1/chat/completions";
        this.model = model != null ? model : "Qwen/Qwen3-Coder-480B-A35B-Instruct-FP8";
        this.maxTokens = maxTokens != null ? maxTokens : 1024;
        this.temperature = temperature != null ? temperature : 0.5;
        this.n = n != null ? n : 1;
    }
}