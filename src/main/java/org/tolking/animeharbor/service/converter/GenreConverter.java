package org.tolking.animeharbor.service.converter;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.tolking.animeharbor.dto.genre.GenreNameDTO;

@Component
@RequiredArgsConstructor
public class GenreConverter implements Converter<String, GenreNameDTO> {

    @SneakyThrows
    @Override
    public GenreNameDTO convert(@Nullable String id) {
        try {
            GenreNameDTO genreNameDTO = new GenreNameDTO();
            assert id != null;
            long genreId = Long.parseLong(id);
            genreNameDTO.setId(genreId);

            return genreNameDTO;
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

    }
}
