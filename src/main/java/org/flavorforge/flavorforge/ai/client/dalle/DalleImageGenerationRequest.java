package org.flavorforge.flavorforge.ai.client.dalle;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DalleImageGenerationRequest(
        String model,
        String prompt,
        int n,
        String size,
        @JsonProperty("response_format") String responseFormat,
        String style
) {
    public DalleImageGenerationRequest(String prompt, int n) {
        this("dall-e-3", prompt, n, "1024x1024", "url", "natural");
    }
}
