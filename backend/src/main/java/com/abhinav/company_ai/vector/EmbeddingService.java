package com.abhinav.company_ai.vector;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class EmbeddingService {

    private final WebClient webClient;

    @Value("${nvidia.api.key}")
    private String apiKey;

    @Value("${nvidia.embedding.url}")
    private String embeddingUrl;

    @Value("${nvidia.embedding.model}")
    private String embeddingModel;

    public EmbeddingService() {
        this.webClient = WebClient.builder().build();
    }

    public float[] generateEmbedding(String text) {

        return generateEmbedding(text, "query");
    }

    public float[] generatePassageEmbedding(String text) {

        return generateEmbedding(text, "passage");
    }

    private float[] generateEmbedding(
            String text,
            String inputType) {

        try {

            Map<String, Object> body = Map.of(
                    "input", List.of(text),
                    "model", embeddingModel,
                    "input_type", inputType
            );

            String response =
                    webClient.post()
                            .uri(embeddingUrl)
                            .header(
                                    HttpHeaders.AUTHORIZATION,
                                    "Bearer " + apiKey
                            )
                            .contentType(
                                    MediaType.APPLICATION_JSON
                            )
                            .bodyValue(body)
                            .retrieve()
                            .bodyToMono(String.class)
                            .block();

            ObjectMapper mapper =
                    new ObjectMapper();

            JsonNode root =
                    mapper.readTree(response);

            JsonNode vector =
                    root.path("data")
                            .get(0)
                            .path("embedding");

            float[] embedding =
                    new float[vector.size()];

            for (int i = 0; i < vector.size(); i++) {

                embedding[i] =
                        vector.get(i).floatValue();
            }

            System.out.println(
                    "Embedding type: " + inputType
            );

            System.out.println(
                    "Embedding size: " + embedding.length
            );

            return embedding;

        } catch (Exception e) {

            e.printStackTrace();

            return new float[0];
        }
    }
}