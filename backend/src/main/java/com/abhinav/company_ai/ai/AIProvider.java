package com.abhinav.company_ai.ai;

import reactor.core.publisher.Flux;

public interface AIProvider {

    String generateResponse(String question, String context);

    Flux<String> generateStream(String question, String context);

}