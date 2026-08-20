package com.abhinav.company_ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class QdrantConfig {

    @Bean
    public WebClient qdrantWebClient() {

        String qdrantUrl = System.getenv("QDRANT_URL");

        if (qdrantUrl == null || qdrantUrl.isBlank()) {
            qdrantUrl = "http://localhost:6333";
        }

        return WebClient.builder()
                .baseUrl(qdrantUrl)
                .build();
    }
}