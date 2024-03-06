package org.flavorforge.flavorforge.ai.image;

import java.util.List;

public interface ImageAiService {
    List<String> generateImage(String description, int numberOfImages);
}
