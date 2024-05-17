package org.tolking.animeharbor.service.seeder;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.tolking.animeharbor.entities.Image;
import org.tolking.animeharbor.entities.enums.ImageType;
import org.tolking.animeharbor.repositories.ImageRepository;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ImageSeeder implements ApplicationListener<ContextRefreshedEvent> {

    public static final String DEFAULT_PROFILE_IMG = "default_profile.jpg";
    public static final String DEFAULT_ANIME_IMG = "default_anime.png";

    private final ImageRepository imageRepository;
    private final Logger logger = LoggerFactory.getLogger(ImageSeeder.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Optional<Image> imageProfileOptional = imageRepository.findByFilename(DEFAULT_PROFILE_IMG);
        Optional<Image> imageAnimeOptional = imageRepository.findByFilename(DEFAULT_ANIME_IMG);

        imageSeeder(imageProfileOptional, DEFAULT_PROFILE_IMG, ImageType.PROFILE);
        imageSeeder(imageAnimeOptional, DEFAULT_ANIME_IMG, ImageType.ANIME);
    }

    private void imageSeeder(Optional<Image> imageOptional, String name, ImageType imageType) {
        if (imageOptional.isPresent()) {
            try {
                Image image = imageOptional.get();
                imageSave(name, imageType, image);

                logger.info("IMAGE OVERWRITE: {}", name);
            } catch (IOException e) {
                notFoundLog(name);
            }

        }else {
            try {
                Image image = new Image();
                imageSave(name, imageType, image);

                logger.warn("IMAGE SAVED: {}", name);
            } catch (IOException e) {
                notFoundLog(name);
            }
        }
    }

    private void imageSave(String name, ImageType imageType, Image image) throws IOException {
        byte[] resource = IOUtils.toByteArray(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("static/img/" + name)));
        String base64 = Base64.getEncoder().encodeToString(resource);


        image.setFilename(name);
        image.setData(base64);
        image.setImageType(imageType);

        imageRepository.save(image);
    }

    private void notFoundLog(String name) {
        logger.error("IMAGE NOT FOUND: {}", name);
    }
}
