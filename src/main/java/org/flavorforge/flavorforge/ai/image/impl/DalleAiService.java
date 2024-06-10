package org.flavorforge.flavorforge.ai.image.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.flavorforge.flavorforge.ai.client.dalle.DalleClient;
import org.flavorforge.flavorforge.ai.client.dalle.DalleImageGenerationRequest;
import org.flavorforge.flavorforge.ai.client.dalle.DalleImageGenerationResponse;
import org.flavorforge.flavorforge.ai.image.ImageAiService;
import org.flavorforge.flavorforge.data.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DalleAiService implements ImageAiService {

    private final DalleClient dalleClient;

    @Value("${dalle.key}")
    private String key;

    @Override
    public Image generateImage(String description) {
        val generatedImages = dalleClient.generateImage(new DalleImageGenerationRequest(description, 1), "Bearer " + key);

        Image image = new Image(generatedImages.data().stream().map(DalleImageGenerationResponse.ImageUrl::url).toList().getFirst());

        return image;
    }
}
