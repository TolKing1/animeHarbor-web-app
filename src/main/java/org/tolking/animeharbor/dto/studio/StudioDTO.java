package org.tolking.animeharbor.dto.studio;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import org.tolking.animeharbor.dto.DTOConverter;
import org.tolking.animeharbor.entities.Studio;
import org.tolking.animeharbor.validator.annotation.UniqueStudioTitle;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class StudioDTO extends DTOConverter<Studio, StudioDTO> {
    private long id;

    @Size(min = 3, max = 30, message = "Length should be between 3 and 30")
    @UniqueStudioTitle
    private String name;

    @Size(min = 3, max = 400, message = "Length should be between 3 and 400")
    private String description;
    private LocalDateTime created;
    private LocalDateTime updated;
    private boolean emptyAnimeList;

    @Override
    protected Class<Studio> getTypeEntity() {
        return Studio.class;
    }

    @Override
    protected Class<StudioDTO> getTypeDTO() {
        return StudioDTO.class;
    }
}
