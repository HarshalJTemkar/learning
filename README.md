Simple AI connection
Client → Controller → ChatService → GUARDRAIL → AI API

How it all fits together
```
ChatRequest
    ↓
EmptyMessageGuardrail     → blocked? return early
    ↓
BannedKeywordGuardrail    → blocked? return early  
    ↓
MessageLengthGuardrail    → blocked? return early
    ↓
AI API Call ✅
```
