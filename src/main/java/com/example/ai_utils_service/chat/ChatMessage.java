package com.example.ai_utils_service.chat;

public class ChatMessage {
    private final String content;
    private final String role;

    public ChatMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getRole() {
        return role;
    }

}
