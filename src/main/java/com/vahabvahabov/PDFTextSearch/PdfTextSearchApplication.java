package com.vahabvahabov.PDFTextSearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableElasticsearchRepositories
public class PdfTextSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(PdfTextSearchApplication.class, args);
	}

}
