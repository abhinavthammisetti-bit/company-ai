package com.abhinav.company_ai.service;

import com.abhinav.company_ai.entity.Document;
import com.abhinav.company_ai.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final ChunkService chunkService;

    public DocumentService(
            DocumentRepository documentRepository,
            ChunkService chunkService) {

        this.documentRepository = documentRepository;
        this.chunkService = chunkService;
    }

    public Document saveDocument(
            String companyName,
            String fileName,
            String content) {

        // Check whether this document already exists
        List<Document> existingDocuments =
                documentRepository.findByCompanyNameAndFileName(
                        companyName,
                        fileName
                );

        if (!existingDocuments.isEmpty()) {

            Document existingDocument = existingDocuments.get(0);

            System.out.println("==================================");
            System.out.println("DOCUMENT ALREADY EXISTS");
            System.out.println("Company : " + companyName);
            System.out.println("File    : " + fileName);
            System.out.println("Existing documents found: "
                    + existingDocuments.size());
            System.out.println("Skipping duplicate upload.");
            System.out.println("==================================");

            return existingDocument;
        }

        // Create new document
        Document document = new Document();

        document.setCompanyName(companyName);
        document.setFileName(fileName);
        document.setContent(content);

        document = documentRepository.save(document);

        // Split and store chunks + embeddings
        chunkService.createChunks(document);

        return document;
    }
}