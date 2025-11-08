package com.vahabvahabov.PDFTextSearch.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
public interface FileStorageService {

    public String storeFile(MultipartFile file) throws IOException;
    public void deleteFile(String filePath) throws IOException;

}
