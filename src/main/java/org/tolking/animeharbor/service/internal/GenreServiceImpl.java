package org.tolking.animeharbor.service.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tolking.animeharbor.entities.Genre;
import org.tolking.animeharbor.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl {
    private final GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
       return genreRepository.findAllByOrderByTitleAsc();
    }

    public Optional<Genre> getByGenre(long id) {
        return genreRepository.findById(id);
    }

}
