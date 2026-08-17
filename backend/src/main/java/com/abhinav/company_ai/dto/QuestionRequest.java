package com.abhinav.company_ai.dto;

import jakarta.validation.constraints.NotBlank;

public class QuestionRequest {

    // Optional now
    private String companyName;

    @NotBlank(message = "Question cannot be empty")
    private String question;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}