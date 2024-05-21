package org.tolking.animeharbor.dto.anime;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.tolking.animeharbor.dto.DTOConverter;
import org.tolking.animeharbor.dto.genre.GenreNameDTO;
import org.tolking.animeharbor.entities.Anime;
import org.tolking.animeharbor.entities.Studio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class AnimeRegisterDTO extends DTOConverter<Anime, AnimeRegisterDTO> {
    private long id;
    @NotBlank(message = "Title can't be blank")
    private String title;
    @NotBlank(message = "Description can't be blank")
    private String description;
    @NotBlank(message = "Type can't be blank")
    private String type;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    @NotBlank(message = "Director name can't be blank")
    private String director;
    @NotBlank(message = "Status can't be blank")
    private String status;
    private List<GenreNameDTO> genre = new ArrayList<>();
    private Studio studio;

    @Override
    protected Class<Anime> getTypeEntity() {
        return Anime.class;
    }

    @Override
    protected Class<AnimeRegisterDTO> getTypeDTO() {
        return AnimeRegisterDTO.class;
    }
}