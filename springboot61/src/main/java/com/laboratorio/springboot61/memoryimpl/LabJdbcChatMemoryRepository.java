package com.laboratorio.springboot61.memoryimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepositoryDialect;
import org.springframework.ai.chat.messages.*;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.lang.Nullable;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class LabJdbcChatMemoryRepository implements ChatMemoryRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate transactionTemplate;
    private final JdbcChatMemoryRepositoryDialect dialect;
    private static final Logger logger = LoggerFactory.getLogger(LabJdbcChatMemoryRepository.class);

    private LabJdbcChatMemoryRepository(JdbcTemplate jdbcTemplate, JdbcChatMemoryRepositoryDialect dialect, PlatformTransactionManager txManager) {
        Assert.notNull(jdbcTemplate, "jdbcTemplate cannot be null");
        Assert.notNull(dialect, "dialect cannot be null");
        this.jdbcTemplate = jdbcTemplate;
        this.dialect = dialect;
        this.transactionTemplate = new TransactionTemplate((PlatformTransactionManager)(txManager != null ? txManager : new DataSourceTransactionManager(jdbcTemplate.getDataSource())));
    }

    public List<String> findConversationIds() {
        return this.jdbcTemplate.queryForList(this.dialect.getSelectConversationIdsSql(), String.class);
    }

    public List<Message> findByConversationId(String conversationId) {
        Assert.hasText(conversationId, "conversationId cannot be null or empty");
        return this.jdbcTemplate.query(this.dialect.getSelectMessagesSql(), new LabJdbcChatMemoryRepository.MessageRowMapper(), new Object[]{conversationId});
    }

    public void saveAll(String conversationId, List<Message> messages) {
        Assert.hasText(conversationId, "conversationId cannot be null or empty");
        Assert.notNull(messages, "messages cannot be null");
        Assert.noNullElements(messages, "messages cannot contain null elements");
        this.transactionTemplate.execute((status) -> {
            this.deleteByConversationId(conversationId);
            this.jdbcTemplate.batchUpdate(this.dialect.getInsertMessageSql(), new LabJdbcChatMemoryRepository.AddBatchPreparedStatement(conversationId, messages));
            return null;
        });
    }

    public void deleteByConversationId(String conversationId) {
        Assert.hasText(conversationId, "conversationId cannot be null or empty");
        this.jdbcTemplate.update(this.dialect.getDeleteMessagesSql(), new Object[]{conversationId});
    }

    public static LabJdbcChatMemoryRepository.Builder builder() {
        return new LabJdbcChatMemoryRepository.Builder();
    }

    private static record AddBatchPreparedStatement(String conversationId, List<Message> messages, AtomicLong instantSeq) implements BatchPreparedStatementSetter {
        private AddBatchPreparedStatement(String conversationId, List<Message> messages) {
            this(conversationId, messages, new AtomicLong(Instant.now().toEpochMilli()));
        }

        public void setValues(PreparedStatement ps, int i) throws SQLException {
            Message message = (Message)this.messages.get(i);
            ps.setString(1, this.conversationId);
            ps.setString(2, message.getText());
            ps.setString(3, message.getMessageType().name());
            LocalDateTime messageTime = (LocalDateTime)message.getMetadata().get("timestamp");
            ps.setTimestamp(4, Timestamp.valueOf(messageTime));
        }

        public int getBatchSize() {
            return this.messages.size();
        }
    }

    private static class MessageRowMapper implements RowMapper<Message> {
        @Nullable
        public Message mapRow(ResultSet rs, int i) throws SQLException {
            String content = rs.getString(1);
            MessageType type = MessageType.valueOf(rs.getString(2));
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("timestamp", rs.getTimestamp("timestamp").toLocalDateTime());
            Object var10000;
            switch (type) {
                case USER -> var10000 = UserMessage.builder()
                        .text(content)
                        .metadata(metadata)
                        .build();
                case ASSISTANT -> var10000 = new AssistantMessage(content, metadata);
                case SYSTEM -> var10000 = SystemMessage.builder()
                        .text(content)
                        .metadata(metadata)
                        .build();
                case TOOL -> var10000 = new ToolResponseMessage(List.of(), metadata);
                default -> throw new IncompatibleClassChangeError();
            }

            return (Message)var10000;
        }
    }

    public static final class Builder {
        private JdbcTemplate jdbcTemplate;
        private JdbcChatMemoryRepositoryDialect dialect;
        private DataSource dataSource;
        private PlatformTransactionManager platformTransactionManager;
        private static final Logger logger = LoggerFactory.getLogger(LabJdbcChatMemoryRepository.Builder.class);

        private Builder() {
        }

        public LabJdbcChatMemoryRepository.Builder jdbcTemplate(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
            return this;
        }

        public LabJdbcChatMemoryRepository.Builder dialect(JdbcChatMemoryRepositoryDialect dialect) {
            this.dialect = dialect;
            return this;
        }

        public LabJdbcChatMemoryRepository.Builder dataSource(DataSource dataSource) {
            this.dataSource = dataSource;
            return this;
        }

        public LabJdbcChatMemoryRepository.Builder transactionManager(PlatformTransactionManager txManager) {
            this.platformTransactionManager = txManager;
            return this;
        }

        public LabJdbcChatMemoryRepository build() {
            DataSource effectiveDataSource = this.resolveDataSource();
            JdbcChatMemoryRepositoryDialect effectiveDialect = this.resolveDialect(effectiveDataSource);
            return new LabJdbcChatMemoryRepository(this.resolveJdbcTemplate(), effectiveDialect, this.platformTransactionManager);
        }

        private JdbcTemplate resolveJdbcTemplate() {
            if (this.jdbcTemplate != null) {
                return this.jdbcTemplate;
            } else if (this.dataSource != null) {
                return new JdbcTemplate(this.dataSource);
            } else {
                throw new IllegalArgumentException("DataSource must be set (either via dataSource() or jdbcTemplate())");
            }
        }

        private DataSource resolveDataSource() {
            if (this.dataSource != null) {
                return this.dataSource;
            } else if (this.jdbcTemplate != null && this.jdbcTemplate.getDataSource() != null) {
                return this.jdbcTemplate.getDataSource();
            } else {
                throw new IllegalArgumentException("DataSource must be set (either via dataSource() or jdbcTemplate())");
            }
        }

        private JdbcChatMemoryRepositoryDialect resolveDialect(DataSource dataSource) {
            if (this.dialect == null) {
                return JdbcChatMemoryRepositoryDialect.from(dataSource);
            } else {
                this.warnIfDialectMismatch(dataSource, this.dialect);
                return this.dialect;
            }
        }

        private void warnIfDialectMismatch(DataSource dataSource, JdbcChatMemoryRepositoryDialect explicitDialect) {
            JdbcChatMemoryRepositoryDialect detected = JdbcChatMemoryRepositoryDialect.from(dataSource);
            if (!detected.getClass().equals(explicitDialect.getClass())) {
                logger.warn("Explicitly set dialect {} will be used instead of detected dialect {} from datasource", explicitDialect.getClass().getSimpleName(), detected.getClass().getSimpleName());
            }

        }
    }
}
