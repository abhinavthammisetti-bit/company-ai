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

        if (question == null || question.trim().isEmpty()) {
            return documents.size() == 1 ? documents.get(0) : null;
        }

        String lowerQuestion = question.toLowerCase().trim();

        // =====================================================
        // STEP 1: Exact/full company-name match
        // =====================================================
        // This MUST happen before individual-word matching.
        // Example:
        // "What is TCS leave policy?"
        // should match "TCS", not "TCS Technologies".
        //
        for (Document document : documents) {

            String company = document.getCompanyName()
                    .toLowerCase()
                    .trim();

            if (containsWholePhrase(lowerQuestion, company)) {
                return document;
            }
        }

        // =====================================================
        // STEP 2: Individual-word matching
        // =====================================================
        // Used only when there was no full company-name match.
        //
        for (Document document : documents) {

            String company = document.getCompanyName()
                    .toLowerCase()
                    .trim();

            for (String word : company.split("\\s+")) {

                if (word.length() > 2 &&
                        containsWholeWord(lowerQuestion, word)) {

                    return document;
                }
            }
        }

        // =====================================================
        // STEP 3: If only one company exists
        // =====================================================
        if (documents.size() == 1) {
            return documents.get(0);
        }

        return null;
    }

    // Check whether a complete phrase exists in the question
    private boolean containsWholePhrase(String text, String phrase) {

        return text.equals(phrase)
                || text.matches(".*\\b" + java.util.regex.Pattern.quote(phrase) + "\\b.*");
    }

    // Check whether a complete word exists in the question
    private boolean containsWholeWord(String text, String word) {

        return text.matches(
                ".*\\b" + java.util.regex.Pattern.quote(word) + "\\b.*"
        );
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