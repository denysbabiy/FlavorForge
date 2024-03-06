package org.flavorforge.flavorforge.ai.client.dalle;

import java.util.List;

public record DalleImageGenerationResponse(
        int created,
        List<ImageUrl> data
) {
    public record ImageUrl(
            String url
    ){
    }
}
