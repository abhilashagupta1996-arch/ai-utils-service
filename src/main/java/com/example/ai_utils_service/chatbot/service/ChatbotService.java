package com.example.ai_utils_service.chatbot.service;

import com.example.ai_utils_service.chat.ChatMessage;
import com.example.ai_utils_service.chat.ConversationStore;
import com.example.ai_utils_service.chatbot.Chatbot;
import com.example.ai_utils_service.chatbot.ChatbotFactory;
import com.example.ai_utils_service.chatbot.ChatbotType;
import com.example.ai_utils_service.client.LlmClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatbotService {

    ChatbotFactory chatbotFactory;
    ConversationStore conversationStore;
    LlmClient llmClient;

    public ChatbotService(ChatbotFactory chatbotFactory, ConversationStore conversationStore, LlmClient llmClient) {
        this.llmClient = llmClient;
        this.chatbotFactory = chatbotFactory;
        this.conversationStore = conversationStore;
    }

    public String chat(ChatbotType chatbotId, String conversationId, String message, boolean cleanResponse) {
        Chatbot chatbot = chatbotFactory.getChatbot(chatbotId);
        String systemPrompt = chatbot.getSystemPrompt();


        List<ChatMessage> history = conversationStore.getConversation(conversationId);
        ChatMessage userChatMessage = new ChatMessage("user", message);
        if (history == null) {
            history = new java.util.ArrayList<>();
            history.add(new ChatMessage("system", systemPrompt));
        }
        history.add(userChatMessage);

        String reply = llmClient.generateWithHistory(history, cleanResponse, chatbotId.getModelName());
        ChatMessage assistantChatMessage = new ChatMessage("assistant", reply);
        history.add(assistantChatMessage);
        conversationStore.setConversation(conversationId, history);

        return reply;
    }
}
