package com.ai.learning.service;

import com.ai.learning.model.ChatRequest;
import com.ai.learning.model.ChatResponse;

public interface AiProvider {

	ChatResponse ask(ChatRequest prompt);
}
