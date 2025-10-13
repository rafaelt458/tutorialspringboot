package com.laboratorio.springboot55.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {
    // private final ChatModel chatModel;
    private final ChatClient chatClient;

    @GetMapping("/question")
    public ResponseEntity<String> answerQuestion(@RequestParam(name = "query") String queryText) {
        // ChatClient chatClient = ChatClient.builder(this.chatModel).build();
        ChatResponse chatResponse = chatClient.prompt(queryText)
                .call().chatResponse();
        String response = chatResponse != null ?
                chatResponse.getResult().getOutput().getText() : "No hubo respuesta";

        return ResponseEntity.ok(response);
    }
}