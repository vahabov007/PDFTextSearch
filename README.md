# PDFTextSearch

A powerful Spring Boot backend service that provides full-text search capabilities on PDF documents using Apache Tika and Elasticsearch.

## Features
- PDF Upload - REST API for uploading and storing PDF files
- Text Extraction - Apache Tika integration for PDF text extraction
- Elasticsearch Indexing - Automatic indexing of extracted text
- Full-text Search - Advanced search capabilities
- Async Processing - Background processing for large files
- Pagination - Support for paginated search results

## Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+
- Elasticsearch 8.x

### 1. Clone & Setup
```bash
git clone https://github.com/yourusername/PDFTextSearch.git
cd PDFTextSearch
```
### 2. Start Elasticsearch
```bash
docker run -d -p 9200:9200 -p 9300:9300 \
  -e "discovery.type=single-node" \
  -e "xpack.security.enabled=false" \
  docker.elastic.co/elasticsearch/elasticsearch:8.11.0
```
### 3. Run Application
```bash
mvn spring-boot:run
```
### API Endpoints
Upload PDF
```bash
POST /api/pdf/upload
Content-Type: multipart/form-data
Body: file=@document.pdf
```
### Upload PDF
```bash
POST /api/pdf/upload
Content-Type: multipart/form-data
Body: file=@document.pdf
```
### Search PDFs
```bash
GET /api/pdf/search?query=spring+boot&page=0&size=10
```
### Get All Documents
```bash
GET /api/pdf/all
```
### Delete Document
```bash
DELETE /api/pdf/{id}
```
### Techonology Stack
Java 17
Spring Boot 3.2.0
Apache Tika 2.9.1
Elasticsearch 8.x
Spring Data Elasticsearch

### Project Structure
```swift
src/main/java/com/example/pdftextsearch/
├── config/
│   ├── ElasticsearchConfig.java
│   └── AsyncConfig.java
├── controller/
│   └── PDFController.java
├── model/
│   └── PDFDocument.java
├── repository/
│   └── PDFDocumentRepository.java
├── service/
│   ├── PDFDocumentService.java
│   ├── PDFTextExtractionService.java
│   └── FileStorageService.java
└── PdfTextSearchApplication.java
```
### Configuration
```bash
server:
  port: 8080

spring:
  data:
    elasticsearch:
      uris: http://localhost:9200

file:
  upload-dir: ./uploads
```
### License 
```bash
This project is licensed under the MIT License.
```
