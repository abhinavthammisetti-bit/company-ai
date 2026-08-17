package com.abhinav.company_ai.controller;

import com.abhinav.company_ai.vector.EmbeddingService;
import com.abhinav.company_ai.vector.QdrantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    private final EmbeddingService embeddingService;
    private final QdrantService qdrantService;

    public SearchController(EmbeddingService embeddingService,
                            QdrantService qdrantService) {

        this.embeddingService = embeddingService;
        this.qdrantService = qdrantService;
    }

    @GetMapping("/search-test")
    public String search() {

        float[] embedding =
                embeddingService.generateEmbedding(
                        "What is the leave policy?"
                );

        return qdrantService.search(
        embedding,
        "Infosys"
);
    }
}