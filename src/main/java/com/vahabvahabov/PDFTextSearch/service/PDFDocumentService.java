package com.vahabvahabov.PDFTextSearch.service;

import com.vahabvahabov.PDFTextSearch.model.PDFDocument;
import org.apache.tika.exception.TikaException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface PDFDocumentService {

    public PDFDocument processAndIndexPdf(MultipartFile file) throws IOException, TikaException;

    public CompletableFuture<PDFDocument> processAndIndexPdfAsync(MultipartFile file);

    public Page<PDFDocument> searchByContent(String query, Pageable pageable);

    public List<PDFDocument> findByFilename(String filename);

    public Optional<PDFDocument> findPdfById(String id);

    public void deleteDocument(String id) throws IOException;

    public List<PDFDocument> findAll();



}
