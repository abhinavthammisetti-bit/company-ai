package com.abhinav.company_ai.controller;

import com.abhinav.company_ai.vector.QdrantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QdrantController {

    private final QdrantService qdrantService;

    public QdrantController(QdrantService qdrantService) {
        this.qdrantService = qdrantService;
    }

    @GetMapping("/create-qdrant")
    public String createCollection() {
        return qdrantService.createCollection();
    }

    @GetMapping("/qdrant-collections")
    public String collections() {
        return qdrantService.getCollections();
    }

    @GetMapping("/qdrant-count")
    public String count() {
        return qdrantService.countPoints();
    }
}