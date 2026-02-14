package com.example.ai_utils_service.controller;

import com.example.ai_utils_service.ResponseFormat;
import com.example.ai_utils_service.chat.ChatRequest;
import com.example.ai_utils_service.service.ConversationService;
import com.example.ai_utils_service.service.LlmService;
import org.springframework.web.bind.annotation.*;

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
    public Map<String, String> askQuestion(@RequestBody Map<String, String> body,
                                           @RequestParam(required = false) boolean cleanResponse) {
        String prompt = body.get("prompt");
        String answer = llmService.ask(prompt, cleanResponse);
        return Map.of("answer", answer);
    }

    @PostMapping("/ask-with-instruction")
    public Map<String, String> askQuestionWithInstruction(@RequestBody Map<String, String> body,
                                                          @RequestParam(required = false) boolean cleanResponse) {
        String prompt = body.get("prompt");
        String instruction = body.get("instruction");
        String answer = llmService.askWithInstruction(prompt, instruction, cleanResponse);
        return Map.of("answer", answer);
    }

    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody ChatRequest body, @RequestParam(required = false) boolean cleanResponse) {
        String answer = conversationService.chat(body.getConversationId(), body.getMessage(), cleanResponse);
        return Map.of("answer", answer);
    }

    @PostMapping("/ask-structured")
    public ResponseFormat askStructured(@RequestBody Map<String, String> body,
                                        @RequestParam(required = false) boolean cleanResponse) {
        ResponseFormat response = llmService.askStructured(body.get("prompt"), cleanResponse);
        return response;
    }
}
