package com.abhinav.company_ai.service;

import com.abhinav.company_ai.entity.AiRequestLog;
import com.abhinav.company_ai.repository.AiRequestLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditLogService {

    private final AiRequestLogRepository repository;

    public AuditLogService(AiRequestLogRepository repository) {
        this.repository = repository;
    }

    public void log(String company,
                    String question,
                    String answer,
                    long responseTime) {

        AiRequestLog log = new AiRequestLog();

        log.setCompanyName(company);
        log.setQuestion(question);
        log.setAnswer(answer);
        log.setModel("NVIDIA Llama 3");
        log.setResponseTime(responseTime);
        log.setCreatedAt(LocalDateTime.now());

        repository.save(log);
    }
}