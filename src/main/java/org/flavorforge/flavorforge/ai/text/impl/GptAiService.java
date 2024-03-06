package org.flavorforge.flavorforge.ai.text.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.collections4.CollectionUtils;
import org.flavorforge.flavorforge.ai.client.gpt.GptClient;
import org.flavorforge.flavorforge.ai.client.gpt.GptRequest;
import org.flavorforge.flavorforge.ai.client.gpt.GptResponse;
import org.flavorforge.flavorforge.ai.text.TextAiService;
import org.flavorforge.flavorforge.data.Recipe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.flavorforge.flavorforge.util.Constants.*;

@Service
@RequiredArgsConstructor
public class GptAiService implements TextAiService {

    private final GptClient gptClient;

    @Value("${gpt.key}")
    private String key;

    @Value("${gpt.temperature}")
    private Double temperature;

    @Override
    public Recipe generateRecipe(List<String> productNames, List<String> excludedProducts, String dishType) {
        var exclude = EXCLUDE_PRODUCTS_NOT_NEEDED_PROMPT;
        if (CollectionUtils.isNotEmpty(excludedProducts)) {
            exclude = String.format(EXCLUDE_PRODUCTS_PROMPT, String.join(DELIMITER, excludedProducts));
        }

        val userInput = String.format(CREATE_RECIPE_PROMPT, dishType, String.join(DELIMITER, productNames))  + exclude + FORMAT_JSON_RULE;

        GptRequest gptRequest = new GptRequest(temperature, List.of(
                new GptRequest.RoleMessage(SYSTEM_ROLE, BASE_RULES),
                new GptRequest.RoleMessage(SYSTEM_ROLE, RECIPE_TASK_RULES.trim()),
                new GptRequest.RoleMessage(USER_ROLE, ASSISTANT_RECIPE_TASK_REQUEST_INPUT),
                new GptRequest.RoleMessage(ASSISTANT_ROLE, ASSISTANT_RECIPE_TASK_RESPONSE_INPUT),
                new GptRequest.RoleMessage(USER_ROLE, userInput)
        ));

        return getChatResponse(gptRequest);
    }

    private Recipe getChatResponse(GptRequest gptRequest) {
        GptResponse gptResponse = gptClient.generateText(gptRequest, key);

        return null;
    }
}
