package com.abhinav.company_ai.application.memory;

import com.abhinav.company_ai.domain.model.Conversation;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConversationManager {

    private final Map<String, Conversation> conversations =
            new ConcurrentHashMap<>();

    public Conversation getConversation(String sessionId) {

        return conversations.computeIfAbsent(
                sessionId,
                id -> new Conversation()
        );

    }

}