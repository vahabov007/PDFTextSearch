package com.vahabvahabov.PDFTextSearch.controller.impl;

import com.vahabvahabov.PDFTextSearch.controller.PDFController;
import com.vahabvahabov.PDFTextSearch.model.PDFDocument;
import com.vahabvahabov.PDFTextSearch.service.PDFDocumentService;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/pdf")
public class PDFControllerImpl implements PDFController {

    @Autowired
    private PDFDocumentService pdfDocumentService;

    @Override
    @PostMapping("/upload")
    public ResponseEntity<?> uploadPdf(@RequestParam("file") MultipartFile file) throws TikaException, IOException {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(createResponse(false, "File is empty."));
            }

            PDFDocument document = pdfDocumentService.processAndIndexPdf(file);

            return ResponseEntity.ok(successDetailedResponse( "PDF uploaded and indexed successfully", document.getId(), document.getFilename()));
        } catch (IOException | TikaException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createResponse(false, "Error processing PDF: " + e.getMessage()));
        }
    }
    @Override
    @PostMapping("/upload-async")
    public ResponseEntity<?> uploadPdfAsync(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            CompletableFuture<PDFDocument> future = pdfDocumentService.processAndIndexPdfAsync(file);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "PDF upload and indexing started asynchronously");
            response.put("filename", file.getOriginalFilename());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createResponse(false, "Error processing PDF: " + e.getMessage()));
        }
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<Page<PDFDocument>> searchPdfs(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PDFDocument> results = pdfDocumentService.searchByContent(query, pageable);

        return ResponseEntity.ok(results);
    }

    @Override
    @GetMapping("/search-by-filename")
    public ResponseEntity<List<PDFDocument>> searchByFilename(@RequestParam String filename) {
        List<PDFDocument> results = pdfDocumentService.findByFilename(filename);
        return ResponseEntity.ok(results);
    }


    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PDFDocument> getDocument(@PathVariable Long id) {
        return pdfDocumentService.findPdfById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @Override
    @GetMapping("/all")
    public ResponseEntity<List<PDFDocument>> getAllDocuments() {
        List<PDFDocument> documents = pdfDocumentService.findAll();
        return ResponseEntity.ok(documents);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable Long id) {
        try {
            pdfDocumentService.deleteDocument(id);
            return ResponseEntity.ok("Document deleted successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting document: " + e.getMessage());
        }
    }

    private Map<String, Object> createResponse(boolean success, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", message);

        return response;
    }

    private Map<String, Object> successDetailedResponse(String message,  Long id, String fileName) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("documentId", id);
        response.put("fileName", fileName);

        return response;
    }
}
