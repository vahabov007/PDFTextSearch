package com.vahabvahabov.PDFTextSearch.service.impl;

import com.vahabvahabov.PDFTextSearch.service.PDFTextExtractService;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class PDFTextExtractServiceImpl implements PDFTextExtractService {

    private  final Tika tika;
    public PDFTextExtractServiceImpl() {
        this.tika = new Tika();
        tika.setMaxStringLength(100 * 1024 * 1024);
    }

    @Override
    public String extractTextFromPdf(MultipartFile file) throws IOException, TikaException {
        if (!isPdfFile(file)) {
            throw new IllegalArgumentException("File must be a PDF");
        }
        try(InputStream inputStream = file.getInputStream()) {
            String text = tika.parseToString(inputStream);
            return text != null ? text.trim() : "";
        }
    }

    @Override
    public String extractTextFromPdf(InputStream inputStream) throws IOException, TikaException {
        String text = tika.parseToString(inputStream);
        return text != null ? text.trim() : "";
    }

    private boolean isPdfFile(MultipartFile file) {
        String contentType = file.getContentType();
        return "application/pdf".equals(contentType) || file.getOriginalFilename().toLowerCase().endsWith(".pdf");
    }
}
