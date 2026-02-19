package com.example.ai_utils_service.client;

import com.example.ai_utils_service.chat.ChatMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface LlmClient {
    String generate(String prompt, boolean cleanResponse);
    String generateWithInstruction(String prompt, String instruction, boolean cleanResponse);
    String generateWithHistory(List<ChatMessage> messages, boolean cleanResponse);
    <T> T generateStructured(String prompt, String instruction, Class<T> responseType, boolean cleanResponse);

    String generateWithHistory(List<ChatMessage> messages, boolean cleanResponse, String modelName);
}
