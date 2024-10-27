package org.flavorforge.flavorforge.ai.text;

import org.flavorforge.flavorforge.data.Recipe;

import java.util.List;

public interface TextAiService {
    Recipe generateRecipe(List<String> productNames, List<String> excludedProducts, String dishType,
                          String languageIsoCode, Boolean isVegetarian, Boolean onlyProvidedIngredients);

    Recipe generateRecipeByImage(String encodedImage, String dishType, String lang);
}
