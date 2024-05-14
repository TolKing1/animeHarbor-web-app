package org.tolking.animeharbor.service;

import org.tolking.animeharbor.entities.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    List<Genre> getAllGenres();

    Optional<Genre> getByGenre(long id);
}
