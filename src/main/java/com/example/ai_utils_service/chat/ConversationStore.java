package com.example.ai_utils_service.chat;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ConversationStore {
    private Map<String, List<ChatMessage>> store = new HashMap();

    public void setConversation(String conversationId, List<ChatMessage> messages) {
        store.put(conversationId, messages);
    }

    public List<ChatMessage> getConversation(String conversationId) {
        return store.get(conversationId);
    }

    public void addMessage(String conversationId, ChatMessage message) {
        List<ChatMessage> messages = store.get(conversationId);
        if(messages == null) {
            messages = new ArrayList<>();
            store.put(conversationId, messages);
        }
        messages.add(message);
    }

    public void clearConversation(String conversationId) {
        store.remove(conversationId);
    }
}
