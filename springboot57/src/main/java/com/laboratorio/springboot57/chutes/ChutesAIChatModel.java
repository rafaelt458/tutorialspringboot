package com.laboratorio.springboot57.chutes;

import com.laboratorio.springboot57.chutes.config.ChutesProperties;
import com.laboratorio.springboot57.chutes.excepcion.ChutesExcepcion;
import com.laboratorio.springboot57.chutes.model.ChutesChatRequest;
import com.laboratorio.springboot57.chutes.model.ChutesChatResponse;
import com.laboratorio.springboot57.chutes.model.ChutesMessage;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@Slf4j
public class ChutesAIChatModel implements ChatModel {
    private final ChutesProperties chutesProperties;
    private final RestClient restClient;

    public ChutesAIChatModel(ChutesProperties chutesProperties) {
        this.chutesProperties = chutesProperties;
        this.restClient = RestClient.builder()
                .baseUrl(this.chutesProperties.getBaseUrl())
                .defaultHeader("Authorization", "Bearer " + this.chutesProperties.getApiKey())
                .build();
    }

    @Override
    @NonNull
    public ChatResponse call(Prompt prompt) {
        try {
            // 1- Convertir el Prompt de Spring AI al formato que espera Chutes
            ChutesChatRequest request = this.createRequestFormPrompt(prompt);

            // 2- Llamar al API de Chutes
            ChutesChatResponse response = this.restClient.post()
                    .uri(this.chutesProperties.getUri())
                    .body(request)
                    .retrieve()
                    .body(ChutesChatResponse.class);

            if (response == null) {
                throw new ChutesExcepcion("La respuesta de Chutes es nula");
            }

            if (response.choices() == null || response.choices().isEmpty()) {
                throw new ChutesExcepcion("No hubo una respuesta válida de Chutes AI");
            }

            // 3- Convertir la respuesta de Chutes al ChatResponse que devuelve Spring AI.
            log.info("Response: {}", response);
            return this.convertResponseToChatResponse(response);
        } catch (Exception e) {
            throw new ChutesExcepcion("Ocurrió un error al llamar el API de Chutes", e);
        }
    }

    private ChutesChatRequest createRequestFormPrompt(Prompt prompt) {
        List<ChutesMessage> messages = prompt.getInstructions().stream()
                .map(message -> new ChutesMessage(
                        message.getMessageType().getValue(),
                        message.getText()
                ))
                .toList();
        ChutesChatRequest request = new ChutesChatRequest(this.chutesProperties.getModel(), messages,
                this.chutesProperties.getMaxTokens(), this.chutesProperties.getTemperature(),
                this.chutesProperties.getN());

        log.info("Request: {}", request);

        return request;
    }

    private @NonNull ChatResponse convertResponseToChatResponse(ChutesChatResponse response) {
        List<Generation> generations = response.choices().stream()
                .map(choice -> new Generation(
                        new AssistantMessage(choice.message().content())
                ))
                .toList();
        return new ChatResponse(generations);
    }
}