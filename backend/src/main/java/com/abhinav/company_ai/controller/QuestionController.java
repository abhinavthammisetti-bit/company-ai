package com.abhinav.company_ai.controller;

import com.abhinav.company_ai.dto.QuestionRequest;
import com.abhinav.company_ai.application.usecase.SendMessageUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin("*")
@Tag(
        name = "Company AI",
        description = "Ask questions from uploaded company documents"
)
public class QuestionController {

    private final SendMessageUseCase sendMessageUseCase;

public QuestionController(SendMessageUseCase sendMessageUseCase) {
    this.sendMessageUseCase = sendMessageUseCase;
}

    @Operation(summary = "Ask question about a company")
    @PostMapping("/ask")
    public String ask(@Valid @RequestBody QuestionRequest request) {

        return sendMessageUseCase.execute(request);
    }
    @PostMapping(
        value = "/ask-stream",
        produces = MediaType.TEXT_EVENT_STREAM_VALUE
)
public Flux<String> askStream(
        @Valid @RequestBody QuestionRequest request) {

    System.out.println("========== STREAM REQUEST ==========");
    System.out.println("Company: " + request.getCompanyName());
    System.out.println("Question: " + request.getQuestion());
    System.out.println("====================================");

    return sendMessageUseCase.executeStream(request);
}
}