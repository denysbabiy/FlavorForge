package org.flavorforge.flavorforge.data;

import java.util.List;

public record RecipeRequest(
        String dishType,
        List<String> ingredients
) {
}
