package com.laboratorio.springboot62.memoryimpl;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.ai.tokenizer.TokenCountEstimator;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TokenWindowChatMemory implements ChatMemory {
    private static final int DEFAULT_MAX_MESSAGES = 20;
    private static final int DEFAULT_MAX_TOKENS = 4096;
    
    private final ChatMemoryRepository chatMemoryRepository;
    private final int maxMessages;
    private final int maxTokens;
    private final TokenCountEstimator tokenCountEstimator;

    private TokenWindowChatMemory(ChatMemoryRepository chatMemoryRepository, int maxMessages, int maxTokens) {
        this.chatMemoryRepository = chatMemoryRepository;
        this.maxMessages = maxMessages;
        this.maxTokens = maxTokens;
        this.tokenCountEstimator = new JTokkitTokenCountEstimator();
    }

    private List<Message> getFilteredMessagesList(List<Message> messages) {
        List<Message> filteredMessages = new ArrayList<>();
        int totalTokens = 0;
        for (int i = messages.size() - 1; i >= 0; i--) {
            Message message = messages.get(i);
            totalTokens += this.tokenCountEstimator.estimate(message.getText());
            if (totalTokens > this.maxTokens) {
                break;
            }
            filteredMessages.add(message);
        }

        return filteredMessages.stream()
                .limit(this.maxMessages)
                .toList();
    }

    @Override
    public void add(@NonNull String conversationId, @NonNull List<Message> messages) {
        // Se agrega la marca de tiempo a cada mensaje nuevo
        messages.forEach(msg -> msg.getMetadata().put("timestamp", LocalDateTime.now()));

        // Se obtiene los mensajes anteriores de la conversación
        List<Message> allMessages = this.chatMemoryRepository.findByConversationId(conversationId);
        allMessages.addAll(messages);

        // Se filtran los mensajes por tiempo y cantidad
        List<Message> filteredMessages = this.getFilteredMessagesList(allMessages);

        // Se actualizan los mensajes en el repositorio
        this.chatMemoryRepository.saveAll(conversationId, filteredMessages);
    }

    @Override
    @NonNull
    public List<Message> get(@NonNull String conversationId) {
        List<Message> allMessages = this.chatMemoryRepository.findByConversationId(conversationId);
        return this.getFilteredMessagesList(allMessages);
    }

    @Override
    public void clear(@NonNull String conversationId) {
        this.chatMemoryRepository.deleteByConversationId(conversationId);
    }

    public static TokenWindowChatMemory.Builder builder() {
        return new TokenWindowChatMemory.Builder();
    }

    public static final class Builder {
        private ChatMemoryRepository chatMemoryRepository;
        private int maxMessages = DEFAULT_MAX_MESSAGES;
        private int maxTokens = DEFAULT_MAX_TOKENS;

        private Builder() {
        }

        public TokenWindowChatMemory.Builder chatMemoryRepository(ChatMemoryRepository chatMemoryRepository) {
            this.chatMemoryRepository = chatMemoryRepository;
            return this;
        }

        public TokenWindowChatMemory.Builder maxMessages(int maxMessages) {
            this.maxMessages = maxMessages;
            return this;
        }

        public TokenWindowChatMemory.Builder maxTokens(int maxTokens) {
            this.maxTokens = maxTokens;
            return this;
        }

        public TokenWindowChatMemory build() {
            if (this.chatMemoryRepository == null) {
                this.chatMemoryRepository = new InMemoryChatMemoryRepository();
            }

            return new TokenWindowChatMemory(this.chatMemoryRepository, this.maxMessages, this.maxTokens);
        }
    }
}
