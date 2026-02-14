package com.example.ai_utils_service.client;

import com.example.ai_utils_service.chat.ChatMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface LlmClient {
    String generate(String prompt);
    String generateWithInstruction(String prompt, String instruction);
    String generateWithHistory(List<ChatMessage> messages);
}
