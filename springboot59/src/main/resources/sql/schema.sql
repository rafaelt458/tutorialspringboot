CREATE TABLE IF NOT EXISTS spring_ai_chat_memory
(
    conversation_id VARCHAR(36) NOT NULL,
    type            VARCHAR(10) NOT NULL,
    content         TEXT        NOT NULL,
    timestamp       TIMESTAMP   NOT NULL,
    PRIMARY KEY (conversation_id, type, timestamp)
);

CREATE INDEX IF NOT EXISTS idx_chat_memory_conversation_id ON spring_ai_chat_memory(conversation_id);