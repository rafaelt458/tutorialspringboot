package com.laboratorio.springboot60.config;

import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepositoryDialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;

public class OracleChatMemoryRepositoryDialect implements JdbcChatMemoryRepositoryDialect {
    @Value("${spring.ai.memory.dialect.oracle.tablename}")
    private String tableName;

    @Override
    @NonNull
    public String getSelectMessagesSql() {
        return "SELECT content, type FROM " + tableName + " WHERE conversation_id = ? ORDER BY message_timestamp";
    }

    @Override
    @NonNull
    public String getInsertMessageSql() {
        return "INSERT INTO " + tableName + " (conversation_id, content, type, message_timestamp) VALUES (?, ?, ?, ?)";
    }

    @Override
    @NonNull
    public String getSelectConversationIdsSql() {
        return "SELECT DISTINCT conversation_id FROM " + tableName;
    }

    @Override
    @NonNull
    public String getDeleteMessagesSql() {
        return "DELETE FROM " + tableName + " WHERE conversation_id = ?";
    }
}