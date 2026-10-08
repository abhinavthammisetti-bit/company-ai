package com.abhinav.company_ai.service;

import com.abhinav.company_ai.ai.AIProvider;
import com.abhinav.company_ai.application.prompt.PromptBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class QuestionService {

    private final RetrievalService retrievalService;
    private final PromptBuilder promptBuilder;
    private final AIProvider aiProvider;
    private final AuditLogService auditLogService;

    public QuestionService(
            RetrievalService retrievalService,
            PromptBuilder promptBuilder,
            AIProvider aiProvider,
            AuditLogService auditLogService) {

        this.retrievalService = retrievalService;
        this.promptBuilder = promptBuilder;
        this.aiProvider = aiProvider;
        this.auditLogService = auditLogService;
    }

    public String askQuestion(
            String companyName,
            String question,
            String conversationContext) {

        long start = System.currentTimeMillis();

        String retrievalQuery =
                """
                Previous Conversation:
                %s

                Current Question:
                %s
                """.formatted(conversationContext, question);

        String context =
                retrievalService.retrieveRelevantContext(
                        companyName,
                        retrievalQuery
                );

        System.out.println("====================================");
        System.out.println("QUESTION:");
        System.out.println(question);

        System.out.println("COMPANY:");
        System.out.println(companyName);

        System.out.println("RETRIEVED CONTEXT:");
        System.out.println(context);

        System.out.println("====================================");

        if (context.isBlank()) {
            return "No relevant company information found.";
        }

        String prompt =
                promptBuilder.build(
        companyName,
        question,
        context
);
                System.out.println("============= FINAL PROMPT =============");
                System.out.println(prompt);
                System.out.println("========================================");
        String answer =
                aiProvider.generateResponse(
                        question,
                        prompt
                );

        long end = System.currentTimeMillis();

        auditLogService.log(
                companyName,
                question,
                answer,
                end - start
        );

        return answer + "\n\n📄 Source: " + companyName + ".pdf";
    }
    public Flux<String> askQuestionStream(
        String companyName,
        String question,
        String conversationContext) {

    String retrievalQuery =
        """
        Previous Conversation:
        %s

        Current Question:
        %s
        """.formatted(conversationContext, question);

    System.out.println("========== RETRIEVAL START ==========");
System.out.println("Company: " + companyName);
System.out.println("Question: " + question);
System.out.println("Retrieval Query: " + retrievalQuery);

String context =
        retrievalService.retrieveRelevantContext(
                companyName,
                retrievalQuery
        );

System.out.println("========== RETRIEVED CONTEXT ==========");
System.out.println(context);
System.out.println("========================================");

    if (context.isBlank()) {
        System.out.println("CONTEXT IS EMPTY!");
        return Flux.just("No relevant company information found.");
    }

    String prompt =
        promptBuilder.build(
                companyName,
                question,
                context
        );
    return aiProvider.generateStream(
            question,
            prompt
    );

}
}