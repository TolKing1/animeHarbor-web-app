package org.tolking.animeharbor.dto.genre;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import org.tolking.animeharbor.dto.DTOConverter;
import org.tolking.animeharbor.entities.Genre;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class GenreNameDTO extends DTOConverter<Genre, GenreNameDTO> {
    private long id;
    private String title;
    private String description;
    private boolean empty;

    @Override
    protected Class<Genre> getTypeEntity() {
        return Genre.class;
    }

    @Override
    protected Class<GenreNameDTO> getTypeDTO() {
        return GenreNameDTO.class;
    }
}
