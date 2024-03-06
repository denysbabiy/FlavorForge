package org.flavorforge.flavorforge.ai.client.gpt;

import java.util.List;

public record GptRequest(
        String model,
        Double temperature,
        List<RoleMessage> messages
) {
    public GptRequest(Double temperature, List<RoleMessage> messages) {
        this("gpt-3.5-turbo", temperature, messages);
    }

    public record RoleMessage(
            String role,
            String content
    ){
    }
}
