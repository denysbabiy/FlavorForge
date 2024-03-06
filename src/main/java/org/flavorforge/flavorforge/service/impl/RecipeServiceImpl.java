package org.flavorforge.flavorforge.service.impl;

import lombok.RequiredArgsConstructor;
import org.flavorforge.flavorforge.ai.text.impl.GptAiService;
import org.flavorforge.flavorforge.data.Image;
import org.flavorforge.flavorforge.data.ImageRequest;
import org.flavorforge.flavorforge.data.Recipe;
import org.flavorforge.flavorforge.data.RecipeRequest;
import org.flavorforge.flavorforge.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final GptAiService gptAiService;

    @Override
    public Recipe generateRecipe(RecipeRequest recipeRequest) {
        return gptAiService.generateRecipe(recipeRequest.ingredients(), null, recipeRequest.dishType());
    }

    @Override
    public Image generateImage(ImageRequest imageRequest) {
        return null;
    }
}
