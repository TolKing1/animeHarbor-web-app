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

    private final ImageRepository imageRepository;
    private final Logger logger = LoggerFactory.getLogger(ImageSeeder.class);
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Optional<Image> imageOptional = imageRepository.findByFilename(DEFAULT_PROFILE_IMG);
        if (imageOptional.isPresent()) {
            logger.info("IMAGE EXISTS: {}", DEFAULT_PROFILE_IMG);
        }else {
            try {
                byte[] resource = IOUtils.toByteArray(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("static/img/" + DEFAULT_PROFILE_IMG)));
                String base64 = Base64.getEncoder().encodeToString(resource);

                Image image = new Image();
                image.setFilename(DEFAULT_PROFILE_IMG);
                image.setData(base64);
                image.setImageType(ImageType.PROFILE);

                imageRepository.save(image);

                logger.warn("IMAGE SAVED: {}", DEFAULT_PROFILE_IMG);
            } catch (IOException e) {
                logger.error("IMAGE NOT FOUND: {}", DEFAULT_PROFILE_IMG);
            }
        }
    }
}
