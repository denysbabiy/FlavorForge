package org.flavorforge.flavorforge.ai.client.gpt;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GptVisionRequest(
        String model,
        Double temperature,
        List<RoleMessage> messages,
        @JsonProperty("response_format")
        ResponseFormat responseFormat
) {
    public GptVisionRequest(Double temperature, List<RoleMessage> messages) {
        this("gpt-4o-mini", temperature, messages, new ResponseFormat("json_object"));
    }

    public record RoleMessage(
        String role,
        List<Object> content
    ){
        public record ContentText(
                String type,
                String text
        ){
        }
        public record ContentImage(
                String type,
                @JsonProperty("image_url")
                ImageUrl imageUrl
        ) {
            public record ImageUrl(
                    String url
            ) {
            }
        }
    }
    public record ResponseFormat(
            String type
    ) {
    }
}
