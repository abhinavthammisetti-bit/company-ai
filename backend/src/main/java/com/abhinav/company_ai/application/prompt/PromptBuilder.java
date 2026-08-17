package com.abhinav.company_ai.application.prompt;

import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {

    public String build(String company, String question, String context) {

        return """
You are CompanyAI.

The following information belongs ONLY to the company:

%s

=============================

DOCUMENT CONTENT

%s

=============================

Question:
%s

Answer ONLY from the document content.

If the answer exists, answer directly.

If the answer does not exist, reply exactly:

I don't have that information.

Do NOT refuse if the answer is clearly present.
""".formatted(company, context, question);

    }

}