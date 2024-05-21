package org.tolking.animeharbor.service.converter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.tolking.animeharbor.dto.DTOConverter;
import org.tolking.animeharbor.dto.studio.StudioDTO;
import org.tolking.animeharbor.entities.Studio;
import org.tolking.animeharbor.exception.StudioNotFoundException;
import org.tolking.animeharbor.repositories.StudioRepository;

@Component
@RequiredArgsConstructor
public class StudioConverter implements Converter<String, Studio> {
    private final StudioRepository studioRepository;
    private final DTOConverter<Studio, StudioDTO> dtoConverter;

    @SneakyThrows
    @Override
    public Studio convert(String id) {
        Long studioId = null;
        try {
            studioId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
        Long finalStudioId = studioId;

        return studioRepository.findById(studioId).orElseThrow( ()->  new StudioNotFoundException("Can't find Studio by id:"+ finalStudioId));
    }
}