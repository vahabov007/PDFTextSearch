package com.vahabvahabov.PDFTextSearch.service.impl;

import com.vahabvahabov.PDFTextSearch.model.PDFDocument;
import com.vahabvahabov.PDFTextSearch.repository.PDFDocumentRepository;
import com.vahabvahabov.PDFTextSearch.service.FileStorageService;
import com.vahabvahabov.PDFTextSearch.service.PDFDocumentService;
import com.vahabvahabov.PDFTextSearch.service.PDFTextExtractService;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.StreamSupport;

@Service
public class PDFDocumentServiceImpl implements PDFDocumentService {

    @Autowired
    private PDFDocumentRepository pdfDocumentRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private PDFTextExtractService pdfTextExtractService;


    @Override
    public PDFDocument processAndIndexPdf(MultipartFile file) throws IOException, TikaException {
        String filePath = fileStorageService.storeFile(file);

        String content = pdfTextExtractService.extractTextFromPdf(file);

        PDFDocument document = new PDFDocument(
                file.getOriginalFilename(),
                content,
                filePath,
                file.getSize()
        );
        return pdfDocumentRepository.save(document);
    }

    @Override
    @Async
    public CompletableFuture<PDFDocument> processAndIndexPdfAsync(MultipartFile file) {
        try {
            PDFDocument document = processAndIndexPdf(file);
            return CompletableFuture.completedFuture(document);
        }catch (Exception e) {
            throw new RuntimeException("Failed to process PDF", e);
        }
    }

    @Override
    public Page<PDFDocument> searchByContent(String query, Pageable pageable) {
        return pdfDocumentRepository.findByContentContaining(query, pageable);
    }

    @Override
    public List<PDFDocument> findByFilename(String filename) {
        return pdfDocumentRepository.findByFilenameContaining(filename);
    }

    @Override
    public Optional<PDFDocument> findPdfById(String id) {
        return pdfDocumentRepository.findById(id);
    }

    @Override
    public void deleteDocument(String  id) throws IOException {
        Optional<PDFDocument> optional = pdfDocumentRepository.findById(id);

        if (optional.isPresent()) {
            fileStorageService.deleteFile(optional.get().getFilePath());
            pdfDocumentRepository.deleteById(id);
        }

    }

    @Override
    public List<PDFDocument> findAll() {
        Iterable<PDFDocument> documents = pdfDocumentRepository.findAll();
        return StreamSupport.stream(documents.spliterator(), false)
                .collect(java.util.stream.Collectors.toList());
    }
}
