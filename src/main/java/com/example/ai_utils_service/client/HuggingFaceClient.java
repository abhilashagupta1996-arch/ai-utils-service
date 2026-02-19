package com.example.ai_utils_service.client;

import com.example.ai_utils_service.chat.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class HuggingFaceClient implements LlmClient{

    private RestTemplate restTemplate = new RestTemplate();
    @Value("${hf.api.key}") String apiKey;
    @Value("${hf.api.url}") String apiUrl;
    @Value("${hf.api.model}") String modelName;


    @Override
    public String generate(String prompt, boolean cleanResponse) {
        Map<String, Object> requestBody = Map.of("model", modelName, "messages", List.of(Map.of("role", "user", "content", prompt)));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        return getResponse(requestEntity, cleanResponse);
    }

    @Override
    public String generateWithInstruction(String prompt, String instruction, boolean cleanResponse) {
        Map<String, Object> requestBody = Map.of("model", modelName, "messages", List.of(Map.of("role", "system", "content", instruction),
                Map.of("role", "user", "content", prompt)));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        return getResponse(requestEntity, cleanResponse);
    }

    @Override
    public String generateWithHistory(List<ChatMessage> messages, boolean cleanResponse) {
        List<Map<String, String>> formattedMessages = messages.stream()
                .map(msg -> Map.of("role", msg.getRole(), "content", msg.getContent()))
                .toList();
        Map<String, Object> requestBody = Map.of("model", modelName, "messages", formattedMessages);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        return getResponse(requestEntity, cleanResponse);
    }

    @Override
    public <T> T generateStructured(String prompt, String instruction, Class<T> responseType, boolean cleanResponse) {
        Map<String, Object> requestBody = Map.of("model", modelName, "messages", List.of(Map.of("role", "system", "content", instruction),
                Map.of("role", "user", "content", prompt)));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        String response = getResponse(requestEntity, cleanResponse);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(response, responseType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse structured response from HuggingFace API", e);
        }
    }

    @Override
    public String generateWithHistory(List<ChatMessage> messages, boolean cleanResponse, String modelName) {
        List<Map<String, String>> formattedMessages = messages.stream()
                .map(msg -> Map.of("role", msg.getRole(), "content", msg.getContent()))
                .toList();
        Map<String, Object> requestBody = Map.of("model", modelName, "messages", formattedMessages);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        return getResponse(requestEntity, cleanResponse);
    }

    private String getResponse(HttpEntity<Map<String, Object>> requestEntity, boolean cleanResponse) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

        if (responseEntity.getBody() == null || responseEntity.getBody().isEmpty()) {
            throw new RuntimeException("No response from HuggingFace API");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap;
        try {
            responseMap = objectMapper.readValue(responseEntity.getBody(), Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse HuggingFace API response", e);
        }

        List<Map<String, Object>> choices = (List<Map<String, Object>>) responseMap.get("choices");

        if(choices.isEmpty()){
            throw new RuntimeException("No choices returned from HuggingFace API");
        }

        Map<String, Object> firstChoice = choices.get(0);
        Object msg = firstChoice.get("message");
        if (!(msg instanceof Map)) {
            throw new RuntimeException("Invalid message format in HuggingFace API response");
        }
        Map<String, Object> msgMap = (Map<String, Object>) msg;

        Object text = msgMap.get("content");
        if(cleanResponse){
            text = cleanResponse(text.toString());
        }
        return text.toString();
    }

    private Object cleanResponse(String text) {
        // Remove code block formatting if present
        //remove **, \n, \t, and other markdown formatting
        if(text == null) return "";
        text = text.replace("\\*\\*", "")
        .replace("\\n", " ")
        .replace("\\t", " ")
        .replace("`", "")
        .replace("json", "");
        return text.trim();
    }
}
