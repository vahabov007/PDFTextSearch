package com.vahabvahabov.PDFTextSearch.service;

import org.apache.tika.exception.TikaException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface PDFTextExtractService {

    public String extractTextFromPdf(MultipartFile file) throws IOException, TikaException;
    public String extractTextFromPdf(InputStream inputStream) throws IOException, TikaException;

}

