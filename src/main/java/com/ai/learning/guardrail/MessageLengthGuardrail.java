package com.ai.learning.guardrail;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.ai.learning.model.ChatRequest;

@Component
@Order(2)
public class MessageLengthGuardrail implements Guardrail {

    private static final int MAX_LENGTH = 500;

    @Override
    public GuardrailResult validate(ChatRequest request) {
        if (request.getMessage().length() > MAX_LENGTH) {
            return new GuardrailResult(false,
                    "Message exceeds max allowed length of " + MAX_LENGTH + " characters");
        }
        return new GuardrailResult(true, null);
    }
}
