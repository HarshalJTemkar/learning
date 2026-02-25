package com.ai.learning.guardrail;

import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.ai.learning.model.ChatRequest;

@Component
@Order(3)
public class BannedKeywordGuardrail implements Guardrail {

    private static final List<String> BANNED_KEYWORDS = List.of(
            "hack", "exploit", "bomb", "malware", "virus"
    );

    @Override
    public GuardrailResult validate(ChatRequest request) {
        String message = request.getMessage().toLowerCase();
        for (String keyword : BANNED_KEYWORDS) {
            if (message.contains(keyword)) {
                return new GuardrailResult(false, "Message contains prohibited content");
            }
        }
        return new GuardrailResult(true, null);
    }
}

