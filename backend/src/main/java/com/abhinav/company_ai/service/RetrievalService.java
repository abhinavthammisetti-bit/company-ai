package com.abhinav.company_ai.service;

import com.abhinav.company_ai.vector.EmbeddingService;
import com.abhinav.company_ai.vector.QdrantService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RetrievalService {

    private final EmbeddingService embeddingService;
    private final QdrantService qdrantService;

    public RetrievalService(
            EmbeddingService embeddingService,
            QdrantService qdrantService) {

        this.embeddingService = embeddingService;
        this.qdrantService = qdrantService;
    }

    public String retrieveRelevantContext(
            String companyName,
            String question) {

        try {

            System.out.println(">>> RETRIEVAL START");

            float[] embedding =
                    embeddingService.generateEmbedding(question);

            System.out.println(">>> EMBEDDING GENERATED: " + embedding.length);

            String response =
                    qdrantService.search(
                            embedding,
                            companyName
                    );

            System.out.println(">>> QDRANT RESPONSE RECEIVED");

            ObjectMapper mapper = new ObjectMapper();

            JsonNode root = mapper.readTree(response);

            JsonNode results = root.path("result").path("points");

            System.out.println(">>> RESULT COUNT: " + results.size());

            StringBuilder context =
                    new StringBuilder();

            Set<String> seenChunks =
                    new HashSet<>();

            for (JsonNode node : results) {

    double score =
            node.path("score").asDouble();

    if (score < 0.30) {
        continue;
    }

    String text =
            node.path("payload")
                    .path("text")
                    .asText();

                if (text == null || text.isBlank()) {
                    continue;
                }

                if (!seenChunks.add(text.trim())) {
                    continue;
                }

                System.out.println(">>> RETRIEVED CHUNK:");
                System.out.println(text);
                System.out.println("-----------------------");

                context.append(text)
                        .append("\n\n");
            }

            System.out.println(">>> CONTEXT CREATED");
            System.out.println(context);

            return context.toString();

        } catch (Exception e) {

            System.out.println(">>> RETRIEVAL ERROR");

            e.printStackTrace();

            return "";
        }
    }
}