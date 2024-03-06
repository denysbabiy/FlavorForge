package org.flavorforge.flavorforge.data;

import java.util.List;

public record Recipe(
        String title,
        List<Ingredient> ingredients,
        String summary,
        List<String> instructions
) {
    public record Ingredient(
            String name,
            String amount
    ){
    }
}
