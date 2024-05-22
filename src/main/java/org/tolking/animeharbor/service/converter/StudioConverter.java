package org.tolking.animeharbor.service.converter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.tolking.animeharbor.dto.studio.StudioAnimeRegisterDTO;

@Component
@RequiredArgsConstructor
public class StudioConverter implements Converter<String, StudioAnimeRegisterDTO> {


    @SneakyThrows
    @Override
    public StudioAnimeRegisterDTO convert(String id) {
        try {
            StudioAnimeRegisterDTO studioDTO = new StudioAnimeRegisterDTO();
            long studioId = Long.parseLong(id);
            studioDTO.setId(studioId);

            return studioDTO;
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

    }
}