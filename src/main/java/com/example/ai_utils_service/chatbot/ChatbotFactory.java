package com.example.ai_utils_service.chatbot;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ChatbotFactory {

    private final Map<String, Chatbot> chatbotMap;

    public ChatbotFactory(List<Chatbot> bots){
        this.chatbotMap = bots.stream().collect(java.util.stream.Collectors.toMap(bot -> bot.getChatbotType().name(), bot -> bot));
    }

    public Chatbot getChatbot(ChatbotType chatbotId) {
        Chatbot chatbot = chatbotMap.get(chatbotId.name());
        if (chatbot == null) {
            throw new IllegalArgumentException("No chatbot found for id: " + chatbotId);
        }
        return chatbot;
    }

    public List<String> listAllChatbots() {
        return chatbotMap.values().stream().map(Chatbot::getChatbotType).map(Enum::name).toList();
    }
}
