package com.laboratorio.springboot66.service;

import com.laboratorio.springboot66.modelo.AIResponse;
import com.laboratorio.springboot66.modelo.DialogRequest;
import com.laboratorio.springboot66.modelo.DialogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DialogService {
    @Value("classpath:prompt/concept_ia.prompt")
    private Resource documentsResource;

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    private List<String> findSimilarConcepts(String query) {
        List<Document> documents = this.vectorStore
                .similaritySearch(
                        SearchRequest.builder()
                                .query(query)
                                .topK(3)
                                .build()
                );

        return documents.stream()
                .map(Document::getFormattedContent)
                .toList();
    }

    public DialogResponse getDialogResponse(DialogRequest request) {
        AIResponse aiResponse = this.chatClient
                .prompt()
                .user(promptUserSpec -> promptUserSpec
                        .text(this.documentsResource)
                        .param("input", request.message())
                        .param("documents", String.join("\n", this.findSimilarConcepts(request.message())))
                )
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, request.userId()))
                .call()
                .entity(AIResponse.class);

        String response = aiResponse != null ?
                aiResponse.response() : "No se obtuvo respuesta";

        return new DialogResponse(request.userId(), response);
    }
}