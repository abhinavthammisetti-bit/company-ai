package com.abhinav.company_ai.service;

import com.abhinav.company_ai.entity.Document;
import com.abhinav.company_ai.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentSelectionService {

    private final DocumentRepository documentRepository;

    public DocumentSelectionService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    // Detect company from user's question
    public Document selectDocument(String question) {

        List<Document> documents = documentRepository.findAll();

        String lowerQuestion = question.toLowerCase();

        for (Document document : documents) {

            String company = document.getCompanyName().toLowerCase();

            // Match full company name
            if (lowerQuestion.contains(company)) {
                return document;
            }

            // Match individual words from company name
            for (String word : company.split("\\s+")) {

                if (word.length() > 2 &&
                        lowerQuestion.contains(word)) {

                    return document;
                }
            }
        }

        // If only one company exists, use it automatically
        if (documents.size() == 1) {
            return documents.get(0);
        }

        return null;
    }

    // Return all available company names
    public List<String> getAvailableCompanies() {

        return documentRepository.findAll()
                .stream()
                .map(Document::getCompanyName)
                .distinct()
                .sorted()
                .toList();
    }

    // Find a document by its company name
    public Document findByCompanyName(String companyName) {

        return documentRepository.findAll()
                .stream()
                .filter(document ->
                        document.getCompanyName()
                                .equalsIgnoreCase(companyName))
                .findFirst()
                .orElse(null);
    }
}