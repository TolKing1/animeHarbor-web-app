package org.tolking.animeharbor.dto.genre;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import org.tolking.animeharbor.dto.DTOConverter;
import org.tolking.animeharbor.entities.Genre;
import org.tolking.animeharbor.validator.annotation.UniqueGenreTitle;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class GenreNameDTO extends DTOConverter<Genre, GenreNameDTO> {
    private long id;

    @Size(min = 3, max = 30, message = "Length should be between 3 and 30")
    @UniqueGenreTitle
    private String title;
    @NotBlank(message = "Description can not be blank")
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
