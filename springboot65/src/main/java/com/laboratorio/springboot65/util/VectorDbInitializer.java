package com.laboratorio.springboot65.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor @Slf4j
public class VectorDbInitializer implements CommandLineRunner {
    @Value("classpath:documents/AI_Concepts.pdf")
    private Resource pdfResource;

    @Value("${spring.ai.vectorstore.pgvector.table-name}")
    private String tableName;

    private final JdbcTemplate jdbcTemplate;
    private final VectorStore vectorStore;

    private long countRows() {
        String sql = "select count(*) from " + this.tableName;
        Long nRows = this.jdbcTemplate.queryForObject(sql, Long.class);
        return nRows != null ? nRows : 0;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Se verifica si es necesario cargar los documentos en la base de datos");

        if (this.countRows() > 0) {
            log.info("No es necesario inicializar la base de datos");
            return;
        }

        log.info("Se inicia la carga de los documentos en la base datos");

        PdfDocumentReaderConfig readerConfig = PdfDocumentReaderConfig.builder()
                .withPageExtractedTextFormatter(
                        ExtractedTextFormatter.builder()
                                .withNumberOfBottomTextLinesToDelete(0)
                                .withNumberOfTopTextLinesToDelete(0)
                                .build()
                )
                .withPagesPerDocument(1)
                .build();

        PagePdfDocumentReader documentReader = new PagePdfDocumentReader(this.pdfResource, readerConfig);
        List<Document> documents = documentReader.get().stream()
                .peek(doc -> log.info("Cargando el documento: {}", doc.getFormattedContent()))
                .toList();

        this.vectorStore.accept(documents);

        log.info("Se ha cargado el documento en la base de datos");
    }
}