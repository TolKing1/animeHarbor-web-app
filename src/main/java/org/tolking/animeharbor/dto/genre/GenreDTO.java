package org.tolking.animeharbor.dto.genre;

import lombok.Data;
import org.tolking.animeharbor.dto.AnimeDTO;

import java.util.List;

@Data
public class GenreDTO {
    private long id;
    private String title;
    private String description;
    private List<AnimeDTO> animeList;
    private boolean empty;
}
