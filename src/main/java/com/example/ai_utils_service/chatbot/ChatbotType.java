package com.example.ai_utils_service.chatbot;

public enum ChatbotType {
    MENTOR("Mentor", "mistralai/Mistral-7B-Instruct-v0.2"),
    NUTRITIONIST("Nutritionist", "mistralai/Mistral-7B-Instruct-v0.2");

    String botType;
    String modelName;

    ChatbotType(String chatbotType, String modelName) {
        this.botType = chatbotType;
        this.modelName = modelName;
    }

    public String getBotType() {
        return botType;
    }

    public String getModelName() {
        return modelName;
    }


}
