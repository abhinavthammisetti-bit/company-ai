package com.abhinav.company_ai.controller;

import com.abhinav.company_ai.service.DocumentService;
import com.abhinav.company_ai.service.PdfService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/document")
@CrossOrigin("*")
public class DocumentController {

    private final PdfService pdfService;
    private final DocumentService documentService;

    public DocumentController(PdfService pdfService,
                              DocumentService documentService) {

        this.pdfService = pdfService;
        this.documentService = documentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPdf(
            @RequestParam("file") MultipartFile file) {

        try {

            // Extract PDF text
            String text = pdfService.extractText(file);
            System.out.println("============== PDF TEXT ==============");
            System.out.println(text);
            System.out.println("======================================");

            // Default company name
            String companyName = "Unknown";

            // Detect company name automatically
            for (String line : text.split("\\R")) {

                if (line.toLowerCase().contains("company name")) {

                    String[] parts = line.split(":");

                    if (parts.length > 1) {
                        companyName = parts[1].trim();
                    }

                    break;
                }
            }

            // Save document
            documentService.saveDocument(
                    companyName,
                    file.getOriginalFilename(),
                    text
            );

            return ResponseEntity.ok(
                    "PDF uploaded successfully for company: " + companyName
            );

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.badRequest()
                    .body("Upload Failed: " + e.getMessage());

        }
    }
}