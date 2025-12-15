package com.laboratorio.springboot63.service;

import com.laboratorio.springboot63.modelo.AIResponse;
import com.laboratorio.springboot63.modelo.DialogRequest;
import com.laboratorio.springboot63.modelo.DialogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DialogService {
    @Value("classpath:prompt/dialog.prompt")
    private Resource dialogResource;

    private final ChatClient chatClient;

    public DialogResponse getDialogResponse(DialogRequest request) {
        AIResponse aiResponse = this.chatClient
                .prompt()
                .user(promptUserSpec -> promptUserSpec
                        .text(this.dialogResource)
                        .param("message", request)
                )
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, request.userId()))
                .call()
                .entity(AIResponse.class);

        String response = aiResponse != null ?
                aiResponse.response() : "No se obtuvo respuesta";

        return new DialogResponse(request.userId(), response);
    }
}