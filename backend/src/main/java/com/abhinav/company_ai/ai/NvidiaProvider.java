package com.abhinav.company_ai.ai;

import com.abhinav.company_ai.service.NvidiaService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class NvidiaProvider implements AIProvider {

    private final NvidiaService nvidiaService;

    public NvidiaProvider(NvidiaService nvidiaService) {
        this.nvidiaService = nvidiaService;
    }

    @Override
    public String generateResponse(String question, String prompt) {

        return nvidiaService.askNvidia(prompt);

    }

    @Override
    public Flux<String> generateStream(String question, String prompt) {
        System.out.println("========== SENDING TO NVIDIA ==========");
System.out.println(prompt);
System.out.println("=======================================");
        return nvidiaService.askNvidiaStream(prompt);

    }

}