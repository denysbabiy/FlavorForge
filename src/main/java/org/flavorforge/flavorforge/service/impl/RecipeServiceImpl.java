package org.flavorforge.flavorforge.service.impl;

import org.flavorforge.flavorforge.data.Image;
import org.flavorforge.flavorforge.data.ImageRequest;
import org.flavorforge.flavorforge.data.Recipe;
import org.flavorforge.flavorforge.data.RecipeRequest;
import org.flavorforge.flavorforge.service.RecipeService;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Override
    public Recipe generateRecipe(RecipeRequest recipeRequest) {
        return null;
    }

    @Override
    public Image generateImage(ImageRequest imageRequest) {
        return null;
    }
}
