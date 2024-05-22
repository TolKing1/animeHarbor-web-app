package org.tolking.animeharbor.service.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tolking.animeharbor.dto.DTOConverter;
import org.tolking.animeharbor.dto.genre.GenreNameDTO;
import org.tolking.animeharbor.dto.genre.GenreTransactionDTO;
import org.tolking.animeharbor.dto.genre.GenreUpdateDTO;
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
    private final DTOConverter<Genre, GenreUpdateDTO> genreUpdateDTOConverter;

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
    public Optional<GenreUpdateDTO> getByGenreForUpdate(long id) {
        Optional<Genre> genreOptional = genreRepository.findById(id);
        return genreOptional.map(genreUpdateDTOConverter::convertToDto);
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
        genreRepository.save(dtoConverterWithoutList.convertToEntity(genreNameDTO));

    }

    @Override
    public void update(GenreUpdateDTO genreUpdateDTO) {
        genreRepository.findById(genreUpdateDTO.getId())
                .ifPresent(genre -> {
                    genre.setTitle(genreUpdateDTO.getTitle());
                    genre.setDescription(genreUpdateDTO.getDescription());

                    genreRepository.save(genre);
                });

    }

}
