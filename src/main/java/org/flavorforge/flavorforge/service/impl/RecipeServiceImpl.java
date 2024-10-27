package org.flavorforge.flavorforge.service.impl;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import lombok.RequiredArgsConstructor;
import org.flavorforge.flavorforge.ai.image.impl.DalleAiService;
import org.flavorforge.flavorforge.ai.text.impl.GptAiService;
import org.flavorforge.flavorforge.data.Image;
import org.flavorforge.flavorforge.data.ImageRequest;
import org.flavorforge.flavorforge.data.Recipe;
import org.flavorforge.flavorforge.data.RecipeRequest;
import org.flavorforge.flavorforge.service.RecipeService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final GptAiService gptAiService;

    private final DalleAiService dalleAiService;

    @Override
    public Recipe generateRecipe(RecipeRequest recipeRequest, String languageIsoCode) {
        return gptAiService.generateRecipe(recipeRequest.ingredients(), null, recipeRequest.dishType(),
                languageIsoCode, recipeRequest.isVegetarian(), recipeRequest.onlyProvidedIngredients());
    }

    @Override
    public Image generateImage(ImageRequest imageRequest) {
        return dalleAiService.generateImage(imageRequest.description());
    }

    @Override
    public Recipe generateRecipeByImage(final MultipartFile image, final String dishType, final String lang) {
        final String encodedImage;

        encodedImage = reduceImageSizeAndEncode(image);

        return gptAiService.generateRecipeByImage(encodedImage, dishType, lang);
    }

    public static String reduceImageSizeAndEncode(final MultipartFile image) {
        try {
            // Завантаження зображення з MultipartFile
            InputStream inputStream = image.getInputStream();
            BufferedImage originalImage = ImageIO.read(inputStream);

            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();

            // Перевірка, чи потрібно змінювати розмір
            if (originalWidth > 800 || originalHeight > 800) {
                // Обчислення нових розмірів збереженням пропорцій
                float aspectRatio = (float) originalWidth / originalHeight;
                int targetWidth = 800;
                int targetHeight = 800;

                if (originalWidth > originalHeight) {
                    targetHeight = Math.round(targetWidth / aspectRatio);
                } else {
                    targetWidth = Math.round(targetHeight * aspectRatio);
                }

                // Зміна розміру зображення
                java.awt.Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, java.awt.Image.SCALE_SMOOTH);
                BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

                Graphics2D g2d = resizedImage.createGraphics();
                g2d.drawImage(scaledImage, 0, 0, null);
                g2d.dispose();

                // Конвертація зображення в байти
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    ImageIO.write(resizedImage, "jpg", baos);
                    byte[] imageBytes = baos.toByteArray();

                    // Кодування в Base64
                    return Base64.getEncoder().encodeToString(imageBytes);
                }
            } else {
                // Якщо розмір менший, ніж 800x800, просто кодуємо в Base64
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    ImageIO.write(originalImage, "jpg", baos);
                    byte[] imageBytes = baos.toByteArray();
                    return Base64.getEncoder().encodeToString(imageBytes);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to process image", e);
        }
    }
}
