package com.example.ai_utils_service;

public class ResponseJsonInstruction {
    public static String build() {
        return """
                You are a JSON API, read-only.
                You must respond ONLY with valid JSON.
                Do not use markdown.
                Do not use code fences.
                Do not use backticks.
                Do not add any explanation outside the JSON.
                
                Use EXACTLY this schema:
                {
                  "title": string,
                  "category": "bug" | "feature" | "question",
                  "priority": "low" | "medium" | "high",
                  "summary": string,
                  "steps": string[]
                }
                
                Rules:
                - title: short, clear title
                - category: infer from the description
                - priority: infer from urgency or impact
                - summary: 1 to 3 sentences
                - steps: at least 3 to 7 concrete action steps
                
                Do not include any fields other than the ones in the schema.
                Do not include any text outside the JSON object.
                """;
    }
}