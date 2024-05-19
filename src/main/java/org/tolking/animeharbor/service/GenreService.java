package org.tolking.animeharbor.service;

import org.tolking.animeharbor.dto.genre.GenreNameDTO;
import org.tolking.animeharbor.dto.genre.GenreTransactionDTO;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    List<GenreNameDTO> getAllGenresOrderByTitle();

    Optional<GenreNameDTO> getByGenre(long id);

    List<GenreTransactionDTO> getAllGenre();

    void delete(GenreNameDTO genreNameDTO);

    void save(GenreNameDTO genreNameDTO);
}
