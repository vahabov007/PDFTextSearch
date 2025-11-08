package com.vahabvahabov.PDFTextSearch.repository;

import com.vahabvahabov.PDFTextSearch.model.PDFDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PDFDocumentRepository extends ElasticsearchRepository<PDFDocument, String> {

    List<PDFDocument> findByFilenameContaining(String filename);

    @Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"content\", \"filename\"]}}")
    Page<PDFDocument> findByContentContaining(String query, Pageable pageable);


}
