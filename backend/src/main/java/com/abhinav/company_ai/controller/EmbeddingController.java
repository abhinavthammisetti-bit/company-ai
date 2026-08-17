package com.abhinav.company_ai.controller;

import com.abhinav.company_ai.vector.EmbeddingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmbeddingController {

    private final EmbeddingService embeddingService;

    public EmbeddingController(EmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }

    @GetMapping("/embedding-test")
    public String test() {

        float[] vector =
                embeddingService.generateEmbedding(
                        "Employees get 25 paid leaves per year."
                );

        return "Embedding Size = " + vector.length;

    }

}