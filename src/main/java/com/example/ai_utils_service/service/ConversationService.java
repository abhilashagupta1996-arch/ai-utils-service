package com.example.ai_utils_service.service;

import com.example.ai_utils_service.chat.ChatMessage;
import com.example.ai_utils_service.chat.ConversationStore;
import com.example.ai_utils_service.client.LlmClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationService {
    private final ConversationStore conversationStore;
    private final LlmClient llmClient;

    public ConversationService(ConversationStore conversationStore, LlmClient llmClient) {
        this.conversationStore = conversationStore;
        this.llmClient = llmClient;
    }

    public String chat(String conversationId, String userMessage) {

        List<ChatMessage> history = conversationStore.getConversation(conversationId);
        ChatMessage userChatMessage = new ChatMessage("user", userMessage);
        if(history==null){
            history = new java.util.ArrayList<>();
        }
        history.add(userChatMessage);

        System.out.println("Conversation history:" + history);
        String reply = llmClient.generateWithHistory(history);
        ChatMessage assistantChatMessage = new ChatMessage("assistant", reply);
        history.add(assistantChatMessage);
        conversationStore.setConversation(conversationId, history);

        System.out.println("Conversation history:" + history);
        return reply;

    }
}
