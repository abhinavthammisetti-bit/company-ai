package com.abhinav.company_ai.controller;

import com.abhinav.company_ai.entity.Document;
import com.abhinav.company_ai.repository.DocumentRepository;
import com.abhinav.company_ai.service.DocumentService;
import com.abhinav.company_ai.service.PdfService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/document")
@CrossOrigin("*")
public class DocumentController {

    private final PdfService pdfService;
    private final DocumentService documentService;
    private final DocumentRepository documentRepository;

    public DocumentController(
            PdfService pdfService,
            DocumentService documentService,
            DocumentRepository documentRepository) {

        this.pdfService = pdfService;
        this.documentService = documentService;
        this.documentRepository = documentRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPdf(
            @RequestParam("file") MultipartFile file) {

        try {

            String text = pdfService.extractText(file);

            System.out.println("============== PDF TEXT ==============");
            System.out.println(text);
            System.out.println("======================================");

            String companyName = "Unknown";

            for (String line : text.split("\\R")) {

                if (line.toLowerCase().contains("company name")) {

                    String[] parts = line.split(":");

                    if (parts.length > 1) {
                        companyName = parts[1].trim();
                    }

                    break;
                }
            }

            documentService.saveDocument(
                    companyName,
                    file.getOriginalFilename(),
                    text
            );

            return ResponseEntity.ok(
                    "PDF uploaded successfully for company: "
                            + companyName
            );

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.badRequest()
                    .body("Upload Failed: " + e.getMessage());
        }
    }

    @PostMapping("/reindex")
    public ResponseEntity<String> reindexDocuments() {

        try {

            List<Document> documents =
                    documentRepository.findAll();

            int count = 0;

            for (Document document : documents) {

                System.out.println(
                        "Re-indexing: "
                                + document.getFileName()
                );

                documentService.reindexDocument(document);

                count++;
            }

            return ResponseEntity.ok(
                    "Re-indexed " + count + " documents successfully."
            );

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.internalServerError()
                    .body("Re-index failed: " + e.getMessage());
        }
    }
}