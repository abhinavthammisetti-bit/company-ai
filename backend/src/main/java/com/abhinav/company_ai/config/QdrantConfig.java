package com.abhinav.company_ai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class QdrantConfig {

    @Value("${qdrant.url}")
    private String qdrantUrl;

    @Value("${qdrant.api.key}")
    private String qdrantApiKey;

    @Bean
    public WebClient qdrantWebClient() {

        return WebClient.builder()
                .baseUrl(qdrantUrl)
                .defaultHeader("api-key", qdrantApiKey)
                .build();
    }
}