package com.ai.learning.service;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.ai.learning.guardrail.Guardrail;
import com.ai.learning.guardrail.GuardrailResult;
import com.ai.learning.model.ChatRequest;
import com.ai.learning.model.ChatResponse;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService implements AiProvider {

    private final ChatClient chatClient;

    // Spring auto-injects ALL Guardrail beans into this list
    private final List<Guardrail> guardrails;

    @Value("classpath:system-prompt.txt")
    private Resource systemPromptResource;

    private String systemPrompt;

    @PostConstruct
    public void init() throws Exception {
        systemPrompt = systemPromptResource.getContentAsString(StandardCharsets.UTF_8);
        if (systemPrompt == null || systemPrompt.isBlank()) {
            systemPrompt = "You are a helpful AI assistant.";
            log.warn("system-prompt.txt is empty, using default system prompt.");
        }
        log.debug("System prompt loaded: {}", systemPrompt);
    }

    @Override
    public ChatResponse ask(ChatRequest request) {

        // Run all guardrails before hitting AI API
        for (Guardrail guardrail : guardrails) {
            GuardrailResult result = guardrail.validate(request);
            if (!result.isAllowed()) {
                log.warn("Request blocked by [{}]: {}", guardrail.getClass().getSimpleName(), result.getReason());
                return new ChatResponse("Request blocked: " + result.getReason());
            }
        }

        // All guardrails passed — call AI
        String response = chatClient.prompt()
                .system(systemPrompt)
                .user(request.getMessage())
                .call()
                .content();

        return new ChatResponse(response);
    }
}
