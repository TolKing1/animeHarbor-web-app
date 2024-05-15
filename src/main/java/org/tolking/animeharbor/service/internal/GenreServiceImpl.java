package org.tolking.animeharbor.service.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tolking.animeharbor.entities.Genre;
import org.tolking.animeharbor.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements org.tolking.animeharbor.service.GenreService {
    private final GenreRepository genreRepository;

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAllByOrderByTitleAsc();
    }

    @Override
    public Optional<Genre> getByGenre(long id) {
        return genreRepository.findById(id);
    }

}
