package org.flavorforge.flavorforge.ai.client.gpt;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GptResponse(
        String id,
        @JsonProperty("object") String objectName,
        long created,
        String model,
        String system_fingerprint,
        Usage usage,
        List<Choice> choices

) {
    public record Usage(
            @JsonProperty("prompt_tokens") int promptTokens,
            @JsonProperty("completion_tokens") int completionTokens,
            @JsonProperty("total_tokens") int totalTokens
    ){
    }

    public record Choice(
            int index,
            Message message,
            @JsonProperty("finish_reason") String finishReason
    ){
        public record Message(
                String role,
                String content
        ){
        }
    }
}
