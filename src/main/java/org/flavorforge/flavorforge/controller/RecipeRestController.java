package org.flavorforge.flavorforge.controller;

import lombok.RequiredArgsConstructor;
import org.flavorforge.flavorforge.data.Image;
import org.flavorforge.flavorforge.data.ImageRequest;
import org.flavorforge.flavorforge.data.Recipe;
import org.flavorforge.flavorforge.data.RecipeRequest;
import org.flavorforge.flavorforge.service.RecipeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/generate")
@RequiredArgsConstructor
public class RecipeRestController {

    private final RecipeService recipeService;

    @PostMapping(value = "/recipe", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Recipe generateRecipe(@RequestBody RecipeRequest recipeRequest, @RequestParam String lang) {
        return recipeService.generateRecipe(recipeRequest, lang);
    }

    @PostMapping(value = "/image", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Image generateImage(@RequestBody ImageRequest imageRequest) {
        return recipeService.generateImage(imageRequest);
    }

    @PostMapping(value = "/recipeByImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Recipe generateRecipeByImage(@RequestBody MultipartFile image, @RequestParam String lang, @RequestParam String dishType) {
        return recipeService.generateRecipeByImage(image, dishType, lang);
    }
}
