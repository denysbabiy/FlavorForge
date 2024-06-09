package org.flavorforge.flavorforge.ai.text.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.collections4.CollectionUtils;
import org.flavorforge.flavorforge.ai.client.gpt.GptClient;
import org.flavorforge.flavorforge.ai.client.gpt.GptRequest;
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
    public Recipe generateRecipe(List<String> productNames, List<String> excludedProducts, String dishType, String languageIsoCode) {
        GptRequest gptRequest;

        if (languageIsoCode.equalsIgnoreCase("ua")) {
            var exclude = EXCLUDE_PRODUCTS_NOT_NEEDED_PROMPT_UA;
            if (CollectionUtils.isNotEmpty(excludedProducts)) {
                exclude = String.format(EXCLUDE_PRODUCTS_PROMPT_UA, String.join(DELIMITER, excludedProducts));
            }

            val userInput = String.format(CREATE_RECIPE_PROMPT_UA, dishType, String.join(DELIMITER, productNames))  + exclude + FORMAT_JSON_RULE_UA;

            gptRequest = new GptRequest(temperature, List.of(
                    new GptRequest.RoleMessage(SYSTEM_ROLE, BASE_RULES_UA),
                    new GptRequest.RoleMessage(SYSTEM_ROLE, RECIPE_TASK_RULES_UA.trim()),
                    new GptRequest.RoleMessage(USER_ROLE, ASSISTANT_RECIPE_TASK_REQUEST_INPUT_UA),
                    new GptRequest.RoleMessage(ASSISTANT_ROLE, ASSISTANT_RECIPE_TASK_RESPONSE_INPUT_UA),
                    new GptRequest.RoleMessage(USER_ROLE, userInput)
            ));
        } else {
            var exclude = EXCLUDE_PRODUCTS_NOT_NEEDED_PROMPT;
            if (CollectionUtils.isNotEmpty(excludedProducts)) {
                exclude = String.format(EXCLUDE_PRODUCTS_PROMPT, String.join(DELIMITER, excludedProducts));
            }

            val userInput = String.format(CREATE_RECIPE_PROMPT, dishType, String.join(DELIMITER, productNames))  + exclude + FORMAT_JSON_RULE;

            gptRequest = new GptRequest(temperature, List.of(
                    new GptRequest.RoleMessage(SYSTEM_ROLE, BASE_RULES),
                    new GptRequest.RoleMessage(SYSTEM_ROLE, RECIPE_TASK_RULES.trim()),
                    new GptRequest.RoleMessage(USER_ROLE, ASSISTANT_RECIPE_TASK_REQUEST_INPUT),
                    new GptRequest.RoleMessage(ASSISTANT_ROLE, ASSISTANT_RECIPE_TASK_RESPONSE_INPUT),
                    new GptRequest.RoleMessage(USER_ROLE, userInput)
            ));
        }

        return getChatResponse(gptRequest);
    }

    private Recipe getChatResponse(GptRequest gptRequest) {
        val gptResponse = gptClient.generateText(gptRequest, "Bearer " + key);

        final String gptMessage = gptResponse.choices().getFirst().message().content();

        val objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(gptMessage, new TypeReference<>(){});
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }
}
