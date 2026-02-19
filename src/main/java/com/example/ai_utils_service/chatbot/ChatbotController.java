package com.example.ai_utils_service.chatbot;

import com.example.ai_utils_service.chatbot.model.ChatRequest;
import com.example.ai_utils_service.chatbot.service.ChatbotService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai/chatbot")
public class ChatbotController {
    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody ChatRequest request, @RequestParam(required = false) Boolean cleanResponse) {
        String answer = chatbotService.chat(request.getChatbotId(), request.getConversationId(), request.getMessage(), cleanResponse);
        return Map.of("answer", answer);
    }
}
