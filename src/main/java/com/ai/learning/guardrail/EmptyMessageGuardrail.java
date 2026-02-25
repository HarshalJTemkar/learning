package com.ai.learning.guardrail;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.ai.learning.model.ChatRequest;

@Component
@Order(1)
public class EmptyMessageGuardrail implements Guardrail {

    @Override
    public GuardrailResult validate(ChatRequest request) {
        if (request.getMessage() == null || request.getMessage().isBlank()) {
            return new GuardrailResult(false, "Message cannot be empty");
        }
        return new GuardrailResult(true, null);
    }
}
