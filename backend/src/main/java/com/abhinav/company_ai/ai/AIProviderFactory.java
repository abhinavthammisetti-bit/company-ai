package com.abhinav.company_ai.ai;

import org.springframework.stereotype.Component;

@Component
public class AIProviderFactory {

    private final NvidiaProvider nvidiaProvider;

    public AIProviderFactory(NvidiaProvider nvidiaProvider) {
        this.nvidiaProvider = nvidiaProvider;
    }

    public AIProvider getProvider() {

        // Later this can return Gemini/OpenAI based on config
        return nvidiaProvider;

    }

}