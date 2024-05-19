package org.tolking.animeharbor.service.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tolking.animeharbor.dto.DTOConverter;
import org.tolking.animeharbor.dto.genre.GenreNameDTO;
import org.tolking.animeharbor.dto.genre.GenreTransactionDTO;
import org.tolking.animeharbor.entities.Genre;
import org.tolking.animeharbor.repositories.GenreRepository;
import org.tolking.animeharbor.service.GenreService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final DTOConverter<Genre, GenreNameDTO> dtoConverterWithoutList;
    private final DTOConverter<Genre, GenreTransactionDTO> dtoConverterTransaction;

    @Override
    public List<GenreNameDTO> getAllGenresOrderByTitle() {
        List<Genre> genreList = genreRepository.findAllByOrderByTitleAsc();
        return dtoConverterWithoutList.convertToDtoList(genreList);
    }

    @Override
    public Optional<GenreNameDTO> getByGenre(long id) {
        Optional<Genre> genreOptional = genreRepository.findById(id);
        return genreOptional.map(dtoConverterWithoutList::convertToDto);
    }

    @Override
    public List<GenreTransactionDTO> getAllGenre() {
        return dtoConverterTransaction.convertToDtoList(genreRepository.findAll());
    }

    @Override
    public void delete(GenreNameDTO genreNameDTO) {
        Genre genre = dtoConverterWithoutList.convertToEntity(genreNameDTO);
        genreRepository.delete(genre);
    }

    @Override
    public void save(GenreNameDTO genreNameDTO) {
        genreRepository.findById(genreNameDTO.getId())
                .ifPresentOrElse(genre -> {
                            genre.setTitle(genreNameDTO.getTitle());
                            genre.setDescription(genreNameDTO.getDescription());

                            genreRepository.save(genre);
                        },
                        () -> genreRepository.save(dtoConverterWithoutList.convertToEntity(genreNameDTO))
                );

    }

}
