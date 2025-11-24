package com.laboratorio.springboot61.memoryimpl;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.messages.Message;
import org.springframework.lang.NonNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

public class TimeWindowChatMemory implements ChatMemory {
    private static final int DEFAULT_MAX_MESSAGES = 20;
    private static final int DEFAULT_MAX_HOURS = 48;

    private final ChatMemoryRepository chatMemoryRepository;
    private final int maxMessages;
    private final Duration maxAge;

    private TimeWindowChatMemory(ChatMemoryRepository chatMemoryRepository, int maxMessages, Duration maxAge) {
        this.chatMemoryRepository = chatMemoryRepository;
        this.maxMessages = maxMessages;
        this.maxAge = maxAge;
    }

    private List<Message> getFilteredMessagesList(List<Message> messages) {
        LocalDateTime limit = LocalDateTime.now().minus(this.maxAge);
        return messages.stream()
                .filter(msg -> ((LocalDateTime)msg.getMetadata().get("timestamp")).isAfter(limit))
                .sorted(Comparator.comparing(msg -> (LocalDateTime)msg.getMetadata().get("timestamp"), Comparator.reverseOrder()))
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
    public List<Message> get(@NonNull String conversationId) {
        List<Message> allMessages = this.chatMemoryRepository.findByConversationId(conversationId);
        return this.getFilteredMessagesList(allMessages);
    }

    @Override
    public void clear(@NonNull String conversationId) {
        this.chatMemoryRepository.deleteByConversationId(conversationId);
    }

    public static TimeWindowChatMemory.Builder builder() {
        return new TimeWindowChatMemory.Builder();
    }

    public static final class Builder {
        private ChatMemoryRepository chatMemoryRepository;
        private int maxMessages = DEFAULT_MAX_MESSAGES;
        private Duration maxAge = Duration.of(DEFAULT_MAX_HOURS, ChronoUnit.HOURS);

        private Builder() {
        }

        public TimeWindowChatMemory.Builder chatMemoryRepository(ChatMemoryRepository chatMemoryRepository) {
            this.chatMemoryRepository = chatMemoryRepository;
            return this;
        }

        public TimeWindowChatMemory.Builder maxMessages(int maxMessages) {
            this.maxMessages = maxMessages;
            return this;
        }

        public TimeWindowChatMemory.Builder maxAge(Duration maxAge) {
            this.maxAge = maxAge;
            return this;
        }

        public TimeWindowChatMemory build() {
            if (this.chatMemoryRepository == null) {
                this.chatMemoryRepository = new InMemoryChatMemoryRepository();
            }

            return new TimeWindowChatMemory(this.chatMemoryRepository, this.maxMessages, this.maxAge);
        }
    }
}