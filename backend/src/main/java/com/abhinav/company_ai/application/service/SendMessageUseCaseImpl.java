package com.abhinav.company_ai.application.service;

import com.abhinav.company_ai.application.usecase.SendMessageUseCase;
import com.abhinav.company_ai.dto.QuestionRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class SendMessageUseCaseImpl implements SendMessageUseCase {

    private final ChatOrchestrator chatOrchestrator;

    public SendMessageUseCaseImpl(ChatOrchestrator chatOrchestrator) {
        this.chatOrchestrator = chatOrchestrator;
    }

    @Override
    public String execute(QuestionRequest request) {

        return chatOrchestrator.process(request);

    }

    @Override
    public Flux<String> executeStream(QuestionRequest request) {

        return chatOrchestrator.processStream(request);

    }
}