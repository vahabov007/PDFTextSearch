package com.vahabvahabov.PDFTextSearch.controller;

import com.vahabvahabov.PDFTextSearch.model.PDFDocument;
import org.apache.coyote.Response;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.PDF;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PDFController {

    public ResponseEntity<?> uploadPdf(MultipartFile file) throws TikaException, IOException;

    public ResponseEntity<?> uploadPdfAsync(MultipartFile file);

    public ResponseEntity<Page<PDFDocument>> searchPdfs(String query, int page, int size);

    public ResponseEntity<List<PDFDocument>> searchByFilename(String filename);

    public ResponseEntity<List<PDFDocument>> getAllDocuments();

    public ResponseEntity<?> deleteDocument(Long id);

    public ResponseEntity<PDFDocument> getDocument(Long id);


}
