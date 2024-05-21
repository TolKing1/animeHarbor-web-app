package org.tolking.animeharbor.dto.studio;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import org.tolking.animeharbor.dto.DTOConverter;
import org.tolking.animeharbor.entities.Studio;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class StudioAnimeRegisterDTO extends DTOConverter<Studio, StudioAnimeRegisterDTO> {
    private long id;
    private String name;

    @Override
    protected Class<Studio> getTypeEntity() {
        return Studio.class;
    }

    @Override
    protected Class<StudioAnimeRegisterDTO> getTypeDTO() {
        return StudioAnimeRegisterDTO.class;
    }
}
