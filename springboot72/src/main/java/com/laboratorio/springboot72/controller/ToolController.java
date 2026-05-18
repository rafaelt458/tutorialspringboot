package com.laboratorio.springboot72.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tools")
@RequiredArgsConstructor
public class ToolController {
    private final ChatModel chatModel;
    private final ToolCallbackProvider mcpToolProvider;

    @GetMapping("/categorias")
    public ResponseEntity<String> getCategories() {
        ChatClient client = ChatClient.create(this.chatModel);

        String response = client.prompt("Recupera el listado de categorías registradas en el sistema")
                .toolCallbacks(this.mcpToolProvider)
                .call()
                .content();

        if (response == null || response.isEmpty()) {
            response = "No hubo respuesta";
        }

        return ResponseEntity.ok(response);
    }
}