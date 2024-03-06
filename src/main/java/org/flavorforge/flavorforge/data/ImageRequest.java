package org.flavorforge.flavorforge.data;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Min;

public record ImageRequest(
        String description,
        @Schema(defaultValue = "1")
        @Min(value = 1)
        int numberOfImages
) {
}
