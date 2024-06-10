package org.flavorforge.flavorforge.ai.image;

import org.flavorforge.flavorforge.data.Image;

public interface ImageAiService {
    Image generateImage(String description);
}
