package com.laboratorio.springboot62.memoryimpl;

import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepositoryDialect;

public class LabPostgresChatMemoryRepositoryDialect implements JdbcChatMemoryRepositoryDialect {
    public String getSelectMessagesSql() {
        return "SELECT content, type, \"timestamp\" FROM SPRING_AI_CHAT_MEMORY WHERE conversation_id = ? ORDER BY \"timestamp\"";
    }

    public String getInsertMessageSql() {
        return "INSERT INTO SPRING_AI_CHAT_MEMORY (conversation_id, content, type, \"timestamp\") VALUES (?, ?, ?, ?)";
    }

    public String getSelectConversationIdsSql() {
        return "SELECT DISTINCT conversation_id FROM SPRING_AI_CHAT_MEMORY";
    }

    public String getDeleteMessagesSql() {
        return "DELETE FROM SPRING_AI_CHAT_MEMORY WHERE conversation_id = ?";
    }
}