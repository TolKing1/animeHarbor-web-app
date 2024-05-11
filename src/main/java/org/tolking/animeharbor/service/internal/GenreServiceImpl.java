package org.tolking.animeharbor.service.internal;

import org.springframework.stereotype.Service;
import org.tolking.animeharbor.entities.Genre;
import org.tolking.animeharbor.entities.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl {
    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAllGenres() {
       return genreRepository.findAllByOrderByTitleAsc();
    }

    public Optional<Genre> getByGenre(long id) {
        return genreRepository.findById(id);
    }

}
