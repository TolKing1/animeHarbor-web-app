package org.tolking.animeharbor.dto.anime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import org.tolking.animeharbor.dto.DTOConverter;
import org.tolking.animeharbor.dto.ImageDTO;
import org.tolking.animeharbor.dto.genre.GenreNameDTO;
import org.tolking.animeharbor.entities.Anime;
import org.tolking.animeharbor.entities.enums.AnimeType;

import java.time.LocalDate;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class AnimeDTO extends DTOConverter<Anime, AnimeDTO> {
    private long id;
    private String title;
    private String description;
    private AnimeType type;
    private LocalDate date;
    private String director;
    private String status;
    private Set<GenreNameDTO> genre;
    private String studioName;
    private ImageDTO image;
    private long viewCount;
    private long ratingCount;
    private double averageRating;

    @Override
    protected Class<Anime> getTypeEntity() {
        return Anime.class;
    }

    @Override
    protected Class<AnimeDTO> getTypeDTO() {
        return AnimeDTO.class;
    }
}
