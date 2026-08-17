package com.abhinav.company_ai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import org.springframework.http.codec.ServerSentEvent;

@Service
public class NvidiaService {

    private final WebClient webClient;

    @Value("${nvidia.api.key}")
    private String apiKey;

    @Value("${nvidia.api.url}")
    private String apiUrl;

    @Value("${nvidia.model}")
    private String model;

    public NvidiaService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String askNvidia(String prompt) {

    try {

        var body = java.util.Map.of(
                "model", model,
                "messages", java.util.List.of(

                        java.util.Map.of(
                                "role", "system",
                                "content",
                                """
                                You are CompanyAI.

                                You MUST answer ONLY using the supplied company context.

                                If the answer is not explicitly present in the context, reply exactly:

                                "I don't have that information in the uploaded documents."

                                Never use your own knowledge.
                                Never invent policies.
                                Never guess.
                                """
        ),

        java.util.Map.of(
                "role", "user",
                "content", prompt
        )

),
                "temperature", 0.0,
                "top_p", 0.7,
                "max_tokens", 1024
        );

        String response = webClient.post()
                .uri(apiUrl)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
                System.out.println("====================================");
System.out.println("RAW NVIDIA RESPONSE:");
System.out.println(response);
System.out.println("====================================");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);

        return root
                .path("choices")
                .get(0)
                .path("message")
                .path("content")
                .asText();

    } catch (Exception e) {
        e.printStackTrace();
        return "Error: " + e.getMessage();
    }
}
public Flux<String> askNvidiaStream(String prompt) {

    var body = java.util.Map.of(
            "model", model,
            "stream", true,
            "messages", java.util.List.of(

                    java.util.Map.of(
                            "role", "system",
                            "content",
                            """
                            You are CompanyAI.

                            Answer ONLY from the supplied company context.

                            Never invent answers.
                            """
                    ),

                    java.util.Map.of(
                            "role", "user",
                            "content", prompt
                    )

            ),
            "temperature", 0.0
    );

    return webClient.post()
            .uri(apiUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.TEXT_EVENT_STREAM)
            .bodyValue(body)
            .retrieve()
            .bodyToFlux(String.class);

}
}