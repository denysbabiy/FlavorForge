package org.flavorforge.flavorforge.ai.image.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.flavorforge.flavorforge.ai.client.dalle.DalleClient;
import org.flavorforge.flavorforge.ai.client.dalle.DalleImageGenerationRequest;
import org.flavorforge.flavorforge.ai.client.dalle.DalleImageGenerationResponse;
import org.flavorforge.flavorforge.ai.image.ImageAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DalleAiService implements ImageAiService {

    private final DalleClient dalleClient;

    @Value("${dalle.key}")
    private String key;

    @Override
    public List<String> generateImage(String description, int numberOfImages) {
        val generatedImages = dalleClient.generateImage(new DalleImageGenerationRequest(description, numberOfImages), "Bearer " + key);

        return generatedImages.data().stream().map(DalleImageGenerationResponse.ImageUrl::url).toList();
    }
}
