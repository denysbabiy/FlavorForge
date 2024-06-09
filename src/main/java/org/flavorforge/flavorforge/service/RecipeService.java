package org.flavorforge.flavorforge.service;

import org.flavorforge.flavorforge.data.Image;
import org.flavorforge.flavorforge.data.ImageRequest;
import org.flavorforge.flavorforge.data.Recipe;
import org.flavorforge.flavorforge.data.RecipeRequest;

public interface RecipeService {
    Recipe generateRecipe(RecipeRequest recipeRequest, String languageIsoCode);

    Image generateImage(ImageRequest imageRequest);
}
