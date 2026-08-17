package com.abhinav.company_ai.application.service;

import com.abhinav.company_ai.application.memory.ConversationManager;
import com.abhinav.company_ai.domain.model.Conversation;
import com.abhinav.company_ai.dto.QuestionRequest;
import com.abhinav.company_ai.entity.Document;
import com.abhinav.company_ai.service.DocumentSelectionService;
import com.abhinav.company_ai.service.QuestionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class ChatOrchestrator {

    private final QuestionService questionService;
    private final ConversationManager conversationManager;
    private final DocumentSelectionService documentSelectionService;

    public ChatOrchestrator(
            QuestionService questionService,
            ConversationManager conversationManager,
            DocumentSelectionService documentSelectionService) {

        this.questionService = questionService;
        this.conversationManager = conversationManager;
        this.documentSelectionService = documentSelectionService;
    }

    public String process(QuestionRequest request) {

    Conversation conversation =
            conversationManager.getConversation("default");

    Document document =
            documentSelectionService.selectDocument(
                    request.getQuestion()
            );

    if (document == null && conversation.hasCompany()) {

        document =
                documentSelectionService.findByCompanyName(
                        conversation.getCurrentCompany()
                );
    }

    if (document == null) {

        List<String> companies =
                documentSelectionService.getAvailableCompanies();

        StringBuilder response = new StringBuilder();

        response.append("I found multiple companies.\n\n");
        response.append("Available companies:\n\n");

        for (String company : companies) {

            response.append("• ")
                    .append(company)
                    .append("\n");
        }

        response.append("\nPlease mention one company.");

        return response.toString();
    }

    conversation.setCurrentCompany(document.getCompanyName());

    // Save context BEFORE adding the new question
    String conversationContext = conversation.getContext();

    conversation.addMessage(
            "USER",
            request.getQuestion()
    );

    String answer =
            questionService.askQuestion(
                    document.getCompanyName(),
                    request.getQuestion(),
                    conversationContext
            );

    conversation.addMessage(
            "ASSISTANT",
            answer
    );

    return answer;
}
public Flux<String> processStream(QuestionRequest request) {

    Conversation conversation =
            conversationManager.getConversation("default");

    Document document =
            documentSelectionService.selectDocument(
                    request.getQuestion()
            );

    if (document == null && conversation.hasCompany()) {

        document =
                documentSelectionService.findByCompanyName(
                        conversation.getCurrentCompany()
                );
    }

    if (document == null) {

        List<String> companies =
                documentSelectionService.getAvailableCompanies();

        StringBuilder response = new StringBuilder();

        response.append("I found multiple companies.\n\n");
        response.append("Available companies:\n\n");

        for (String company : companies) {

            response.append("• ")
                    .append(company)
                    .append("\n");
        }

        response.append("\nPlease mention one company.");

        return Flux.just(response.toString());
    }

    conversation.setCurrentCompany(document.getCompanyName());

    String conversationContext =
            conversation.getContext();

    conversation.addMessage(
            "USER",
            request.getQuestion()
    );

    return questionService.askQuestionStream(
            document.getCompanyName(),
            request.getQuestion(),
            conversationContext
    );
}
}