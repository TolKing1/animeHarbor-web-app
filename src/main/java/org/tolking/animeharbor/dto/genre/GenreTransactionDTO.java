package org.tolking.animeharbor.dto.genre;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import org.tolking.animeharbor.dto.DTOConverter;
import org.tolking.animeharbor.entities.Genre;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class GenreTransactionDTO extends DTOConverter<Genre, GenreTransactionDTO> {
    private long id;
    private String title;
    private String description;
    private boolean empty;

    private LocalDateTime created;
    private LocalDateTime updated;

    @Override
    protected Class<Genre> getTypeEntity() {
        return Genre.class;
    }

    @Override
    protected Class<GenreTransactionDTO> getTypeDTO() {
        return GenreTransactionDTO.class;
    }
}
