package com.vahabvahabov.PDFTextSearch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.time.LocalDateTime;

@Document(indexName = "pdf_document")
@Data
@NoArgsConstructor
public class PDFDocument {
    @Id
    private String id;

    private String filename;

    private String content;

    private String filePath;

    @Field(type = FieldType.Date, format = DateFormat.epoch_millis)
    private Instant uploadDate;
    private Long fileSize;

    public PDFDocument(String filename, String content, String filePath, Long fileSize) {
        this.filename = filename;
        this.content = content;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.uploadDate = Instant.now();
    }

}
