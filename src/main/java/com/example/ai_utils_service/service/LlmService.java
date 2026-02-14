package com.example.ai_utils_service.service;

import com.example.ai_utils_service.ResponseFormat;
import com.example.ai_utils_service.ResponseJsonInstruction;
import com.example.ai_utils_service.client.LlmClient;
import org.springframework.stereotype.Service;


@Service
public class LlmService {

    private final LlmClient llmClient;


    public LlmService(LlmClient llmClient) {
        this.llmClient = llmClient;
    }

    public String ask(String prompt, boolean cleanResponse) {
        return llmClient.generate(prompt, cleanResponse);
    }

    public String askWithInstruction(String prompt, String instruction , boolean cleanResponse) {
        return llmClient.generateWithInstruction(prompt, instruction, cleanResponse);
    }

    public ResponseFormat askStructured(String prompt, boolean cleanResponse) {
        return llmClient.generateStructured(prompt, ResponseJsonInstruction.build(), ResponseFormat.class, cleanResponse);
    }

}
