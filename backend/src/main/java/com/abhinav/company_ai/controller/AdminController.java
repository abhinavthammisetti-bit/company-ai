package com.abhinav.company_ai.controller;

import com.abhinav.company_ai.entity.Document;
import com.abhinav.company_ai.repository.DocumentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {

    private final DocumentRepository documentRepository;

    public AdminController(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @GetMapping("/documents")
    public List<Document> getDocuments() {
        return documentRepository.findAll();
    }
    @DeleteMapping("/documents/{id}")
public ResponseEntity<String> deleteDocument(@PathVariable Long id) {

    if (!documentRepository.existsById(id)) {
        return ResponseEntity.notFound().build();
    }

    documentRepository.deleteById(id);

    return ResponseEntity.ok("Document deleted successfully: " + id);
}
}