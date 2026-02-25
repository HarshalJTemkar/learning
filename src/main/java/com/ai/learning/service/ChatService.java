package com.ai.learning.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.ai.learning.model.ChatRequest;
import com.ai.learning.model.ChatResponse;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService implements AiProvider {

    private final ChatClient chatClient;

    // FIX: Actually read the system prompt from the file instead of hardcoding it
    @Value("classpath:system-prompt.txt")
    private Resource systemPromptResource;

    private String systemPrompt;

    @PostConstruct
    public void init() throws Exception {
        systemPrompt = systemPromptResource.getContentAsString(StandardCharsets.UTF_8);
        if (systemPrompt == null || systemPrompt.isBlank()) {
            systemPrompt = "You are a helpful AI assistant."; // fallback default
            log.warn("system-prompt.txt is empty, using default system prompt.");
        }
        log.debug("System prompt loaded: {}", systemPrompt);
    }

    @Override
    public ChatResponse ask(ChatRequest request) {
        String response = chatClient.prompt()
                .system(systemPrompt)
                .user(request.getMessage())
                .call()
                .content();

        return new ChatResponse(response);
    }
}
