package com.example.ai_utils_service.service;

import com.example.ai_utils_service.client.LlmClient;
import org.springframework.stereotype.Service;


@Service
public class LlmService {

    private final LlmClient llmClient;


    public LlmService(LlmClient llmClient) {
        this.llmClient = llmClient;
    }

    public String ask(String prompt) {
        return llmClient.generate(prompt);
    }

    public String askWithInstruction(String prompt, String instruction) {
        return llmClient.generateWithInstruction(prompt, instruction);
    }

}
