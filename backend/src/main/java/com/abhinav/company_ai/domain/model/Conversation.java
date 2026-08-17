package com.abhinav.company_ai.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Conversation {

    private final List<String> history = new ArrayList<>();

    private String currentCompany;

    public void addMessage(String role, String message) {

        history.add(role + ": " + message);

    }

    public String getContext() {

        return String.join("\n", history);

    }

    public void setCurrentCompany(String company) {
        this.currentCompany = company;
    }

    public String getCurrentCompany() {
        return currentCompany;
    }

    public boolean hasCompany() {
        return currentCompany != null && !currentCompany.isBlank();
    }

    public void clear() {
        history.clear();
        currentCompany = null;
    }
}