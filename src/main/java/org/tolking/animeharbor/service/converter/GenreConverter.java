package org.tolking.animeharbor.service.converter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.tolking.animeharbor.dto.DTOConverter;
import org.tolking.animeharbor.dto.genre.GenreNameDTO;
import org.tolking.animeharbor.entities.Genre;
import org.tolking.animeharbor.repositories.GenreRepository;

@Component
@RequiredArgsConstructor
public class GenreConverter implements Converter<String, GenreNameDTO> {
    private final GenreRepository genreRepository;
    private final DTOConverter<Genre, GenreNameDTO> dtoConverter;

    @SneakyThrows
    @Override
    public GenreNameDTO convert(String id) {
        try {
            GenreNameDTO genreNameDTO = new GenreNameDTO();
            long genreId = Long.parseLong(id);
            genreNameDTO.setId(genreId);

            return genreNameDTO;
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

    }
}
