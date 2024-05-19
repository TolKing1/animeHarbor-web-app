package org.tolking.animeharbor.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import org.tolking.animeharbor.entities.Image;
import org.tolking.animeharbor.entities.enums.ImageType;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class ImageDTO extends DTOConverter<Image, ImageDTO> {
    private long id;
    private String filename;
    private ImageType imageType;

    @Override
    protected Class<Image> getTypeEntity() {
        return Image.class;
    }

    @Override
    protected Class<ImageDTO> getTypeDTO() {
        return ImageDTO.class;
    }
}
