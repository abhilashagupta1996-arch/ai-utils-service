package com.example.ai_utils_service.chatbot.model;

import com.example.ai_utils_service.chatbot.ChatbotType;

public class ChatRequest {
    private ChatbotType chatbotId;
    private String conversationId;
    private String message;

    public ChatRequest() {
    }

    public ChatRequest(ChatbotType chatbotId, String conversationId, String message) {
        this.chatbotId = chatbotId;
        this.conversationId = conversationId;
        this.message = message;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getMessage() {
        return message;
    }


    public ChatbotType getChatbotId() {
        return chatbotId;
    }

    public void setChatbotId(ChatbotType chatbotId) {
        this.chatbotId = chatbotId;
    }
}
