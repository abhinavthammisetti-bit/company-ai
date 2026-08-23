package com.abhinav.company_ai.vector;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QdrantService {

    private final WebClient qdrantWebClient;

    public QdrantService(WebClient qdrantWebClient) {
        this.qdrantWebClient = qdrantWebClient;
    }

    // =========================
    // Create Collection
    // =========================
    public String createCollection() {

        Map<String, Object> body = Map.of(
                "vectors",
                Map.of(
                        "size", 1024,
                        "distance", "Cosine"
                )
        );

        return qdrantWebClient.put()
                .uri("/collections/company_ai")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    // =========================
    // Store Vector
    // =========================
    public String storeVector(
        String id,
        float[] embedding,
        String company,
        String text) {

        System.out.println("==================================");
        System.out.println("Uploading vector to Qdrant...");
        System.out.println("Chunk ID : " + id);
        System.out.println("Vector Size : " + embedding.length);

        List<Float> vector = new ArrayList<>();

        for (float value : embedding) {
            vector.add(value);
        }
        System.out.println("==================================");
System.out.println("Company : " + company);
System.out.println("Text    : " + text);

Map<String, Object> payload = Map.of(
        "company", company,
        "text", text
);

System.out.println("Payload : " + payload);
        Map<String, Object> point = Map.of(
        "id", Integer.parseInt(id),
        "vector", vector,
        "payload", payload
);

        Map<String, Object> body = Map.of(
                "points",
                List.of(point)
        );

        String response = qdrantWebClient.put()
                .uri("/collections/company_ai/points?wait=true")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("Qdrant Response:");
        System.out.println(response);
        System.out.println("==================================");

        return response;
    }

    // =========================
    // Search Similar Chunks
    // =========================
    public String search(float[] embedding, String company) {

    System.out.println("==================================");
    System.out.println("Searching Qdrant...");
    System.out.println("Company : " + company);

    List<Float> vector = new ArrayList<>();

    for (float value : embedding) {
        vector.add(value);
    }

    Map<String, Object> filter = Map.of(
            "must", List.of(
                    Map.of(
                            "key", "company",
                            "match", Map.of(
                                    "value", company
                            )
                    )
            )
    );

    Map<String, Object> body = Map.of(
        "query", vector,
        "limit", 20,
        "with_payload", true,
        "filter", filter
);

    String response = qdrantWebClient.post()
            .uri("/collections/company_ai/points/query")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(body)
            .retrieve()
            .bodyToMono(String.class)
            .block();

    System.out.println("========== QDRANT SEARCH RESPONSE ==========");
    System.out.println(response);
    System.out.println("=============================================");

    return response;
}

    // =========================
    // Health Check
    // =========================
    public String healthCheck() {

        return qdrantWebClient.get()
                .uri("")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
    public String getCollections() {

    return qdrantWebClient.get()
            .uri("/collections")
            .retrieve()
            .bodyToMono(String.class)
            .block();
}
public String countPoints() {

    Map<String, Object> body = Map.of(
            "exact", true
    );

    return qdrantWebClient.post()
            .uri("/collections/company_ai/points/count")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(body)
            .retrieve()
            .bodyToMono(String.class)
            .block();
}
}