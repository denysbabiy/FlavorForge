package org.flavorforge.flavorforge.controller;

import lombok.RequiredArgsConstructor;
import org.flavorforge.flavorforge.data.Image;
import org.flavorforge.flavorforge.data.ImageRequest;
import org.flavorforge.flavorforge.data.Recipe;
import org.flavorforge.flavorforge.data.RecipeRequest;
import org.flavorforge.flavorforge.service.RecipeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/recipe/generate")
@RequiredArgsConstructor
public class RecipeRestController {

    private RecipeService recipeService;

    @PostMapping(value = "/recipe", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Recipe generateRecipe(@RequestBody RecipeRequest recipeRequest) {
        return recipeService.generateRecipe(recipeRequest);
    }

    @PostMapping(value = "/image", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Image generateImage(@RequestBody ImageRequest imageRequest) {
        return recipeService.generateImage(imageRequest);
    }
}
