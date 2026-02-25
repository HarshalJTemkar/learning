package com.ai.learning.guardrail;

import com.ai.learning.model.ChatRequest;

public interface Guardrail {

    GuardrailResult validate(ChatRequest request);
}
