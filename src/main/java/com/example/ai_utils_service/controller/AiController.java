package com.example.ai_utils_service.controller;

import com.example.ai_utils_service.chat.ChatRequest;
import com.example.ai_utils_service.service.ConversationService;
import com.example.ai_utils_service.service.LlmService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AiController {
    private final LlmService llmService;
    private final ConversationService conversationService;

    public AiController(LlmService llmService, ConversationService conversationService) {
        this.llmService = llmService;
        this.conversationService = conversationService;
    }

    @PostMapping("/ask")
    public Map<String, String> askQuestion(@RequestBody Map<String, String> body) {
        String prompt = body.get("prompt");
        String answer = llmService.ask(prompt);
        return Map.of("answer", answer);
    }

    @PostMapping("/ask-with-instruction")
    public Map<String, String> askQuestionWithInstruction(@RequestBody Map<String, String> body) {
        String prompt = body.get("prompt");
        String instruction = body.get("instruction");
        String answer = llmService.askWithInstruction(prompt, instruction);
        return Map.of("answer", answer);
    }

    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody ChatRequest body) {
        String answer = conversationService.chat(body.getConversationId(), body.getMessage());
        return Map.of("answer", answer);
    }
}
