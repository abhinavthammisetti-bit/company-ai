package com.abhinav.company_ai.application.usecase;

import com.abhinav.company_ai.dto.QuestionRequest;
import reactor.core.publisher.Flux;

public interface SendMessageUseCase {

    String execute(QuestionRequest request);

    Flux<String> executeStream(QuestionRequest request);

}