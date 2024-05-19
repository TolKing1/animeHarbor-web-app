package org.tolking.animeharbor.service;

import org.tolking.animeharbor.dto.genre.GenreNameDTO;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    List<GenreNameDTO> getAllGenresOrderByTitle();

    Optional<GenreNameDTO> getByGenre(long id);
}
