package com.vahabvahabov.PDFTextSearch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Document(indexName = "pdf_document")
@Data
@NoArgsConstructor
public class PDFDocument {
    @Id
    private Long id;

    private String filename;

    private String content;

    private String filePath;

    private LocalDateTime uploadDate;
    private Long fileSize;

    public PDFDocument(String filename, String content, String filePath, Long fileSize) {
        this.filename = filename;
        this.content = content;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.uploadDate = LocalDateTime.now();
    }

}
