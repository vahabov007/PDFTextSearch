package com.vahabvahabov.PDFTextSearch.service.impl;

import com.vahabvahabov.PDFTextSearch.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    @Value("${file.upload-dir}")
    private String uploadDir;


    @Override
    public String storeFile(MultipartFile file) throws IOException {
        Path uploadedPath = Paths.get(uploadDir);
        if(!Files.exists(uploadedPath)) {
            Files.createDirectories(uploadedPath);
        }
        String orginalFileName = file.getOriginalFilename();
        String fileExtension = orginalFileName != null ? orginalFileName.substring(orginalFileName.lastIndexOf(".")) : ".pdf";
        String fileName = UUID.randomUUID().toString() + fileExtension;

        Path filePath = uploadedPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);
        return filePath.toString();
    }

    @Override
    public void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            Files.delete(path);
        }

    }
}
