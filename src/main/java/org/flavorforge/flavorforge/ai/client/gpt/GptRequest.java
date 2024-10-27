package org.flavorforge.flavorforge.ai.client.gpt;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GptRequest(
        String model,
        Double temperature,
        List<RoleMessage> messages,
        @JsonProperty("response_format")
        ResponseFormat responseFormat
) {
    public GptRequest(Double temperature, List<RoleMessage> messages) {
        this("gpt-4o-mini", temperature, messages, new ResponseFormat("json_object"));
    }

    public record RoleMessage(
            String role,
            String content
    ){
    }
    public record ResponseFormat(
            String type
    ) {
    }
}
