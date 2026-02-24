package com.ai.learning.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.ai.learning.model.ChatRequest;
import com.ai.learning.model.ChatResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService implements AiProvider{

    private final ChatClient chatClient;

    @Override
    public ChatResponse ask(ChatRequest request) {

        String response = chatClient.prompt()
        		.system("You are a helpful AI assistant.") /** Read from system-prompt.txt*/
                .user(request.getMessage())
                .call()
                .content();

        return new ChatResponse(response);
    }
}
