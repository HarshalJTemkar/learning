package com.ai.learning.guardrail;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GuardrailResult {

    private boolean allowed;
    private String reason; // populated only when blocked
}