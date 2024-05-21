package org.tolking.animeharbor.dto.anime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import org.tolking.animeharbor.dto.DTOConverter;
import org.tolking.animeharbor.dto.genre.GenreNameDTO;
import org.tolking.animeharbor.entities.Anime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class AnimeAdminPageDTO extends DTOConverter<Anime, AnimeAdminPageDTO> {
    private long id;
    private String title;
    private String description;
    private String type;
    private LocalDate date;
    private String director;
    private String status;
    private Set<GenreNameDTO> genre;
    private String studioName;
    private long viewCount;
    private long ratingCount;
    private double averageRating;

    private LocalDateTime created;
    private LocalDateTime updated;

    @Override
    protected Class<Anime> getTypeEntity() {
        return Anime.class;
    }

    @Override
    protected Class<AnimeAdminPageDTO> getTypeDTO() {
        return AnimeAdminPageDTO.class;
    }
}
