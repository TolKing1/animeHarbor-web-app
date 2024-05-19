package org.tolking.animeharbor.service.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.tolking.animeharbor.dto.AnimeDTO;
import org.tolking.animeharbor.dto.DTOConverter;
import org.tolking.animeharbor.entities.Anime;
import org.tolking.animeharbor.repositories.AnimeRepository;
import org.tolking.animeharbor.service.AnimeService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimeServiceImpl implements AnimeService {
    private final AnimeRepository animeRepository;
    private final DTOConverter<Anime, AnimeDTO> dtoConverter;

    private static Pageable getPageable(int pageNo, int pageSize, String sortField, String sortDirection) {
        return PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
    }

    @Override
    public Optional<AnimeDTO> getAnimeById(long id) {
        Optional<Anime> animeOptional = animeRepository.findById(id);

        return animeOptional.map(dtoConverter::convertToDto);
    }

    @Override
    public List<AnimeDTO> getAllAnime(int pageNo, int pageSize, String sortField, String sortDirection) {
        Pageable pageable = getPageable(pageNo, pageSize, sortField, sortDirection);
        return dtoConverter.convertToDtoList(animeRepository.getAllBy(pageable));
    }

    @Override
    public Page<AnimeDTO> searchAnime(String query, int pageNo, int pageSize, String sortField, String sortDirection) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return sortedAnimeDTOPage(query, pageNo, pageSize, sortField, sortDirection, pageable);
    }

    @Override
    public List<AnimeDTO> getAllForRecentlyAddedPage() {
        return getAllAnime(0, 6, "created", "desc");
    }

    @Override
    public List<AnimeDTO> getAllForPopularityPage() {
        Pageable pageable = PageRequest.of(0, 6);
        List<Anime> animeList = animeRepository.getAllByPopularityForLast3Month(pageable);

        return dtoConverter.convertToDtoList(animeList);
    }

    @Override
    public List<AnimeDTO> getAllForTopViewPage() {
        List<Anime> animeList = animeRepository.getAllByOrderByViews(PageRequest.of(0, 6));

        return dtoConverter.convertToDtoList(animeList);
    }

    @Override
    public Page<AnimeDTO> getSortedAnimeDTOPageByGenre(long genreId, int pageNo, int pageSize, String sortField, String sortDirection) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return sortAnimeDTOPageByGenreId(genreId, pageNo, pageSize, sortField, sortDirection, pageable);
    }

    private Page<AnimeDTO> sortedAnimeDTOPage(String query, int pageNo, int pageSize, String sortField, String sortDirection, Pageable pageable) {
        Page<Anime> page;
        if (sortField.equalsIgnoreCase("views")) {
            page = getAnimePageByView(sortDirection, animeRepository.searchFullTextOrderByCountViewsDesc(query, pageable), animeRepository.searchFullTextOrderByCountViewsAsc(query, pageable));
        } else if (sortField.equalsIgnoreCase("rating")) {
            page = getAnimePageByView(sortDirection, animeRepository.searchFullTextOrderByAverageRatingScoreDesc(query, pageable), animeRepository.searchFullTextOrderByAverageRatingScoreAsc(query, pageable));
        } else {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
            page = animeRepository.searchFullText(query, pageable);
        }
        return page.map(dtoConverter::convertToDto);
    }

    private Page<AnimeDTO> sortAnimeDTOPageByGenreId(long genreId, int pageNo, int pageSize, String sortField, String sortDirection, Pageable pageable) {
        Page<Anime> page;
        if (sortField.equalsIgnoreCase("views")) {
            page = getAnimePageByView(sortDirection, animeRepository.findByGenreIdOrderByCountViewsDesc(genreId, pageable), animeRepository.findByGenreIdOrderByCountViewsAsc(genreId, pageable));
        } else if (sortField.equalsIgnoreCase("rating")) {
            page = getAnimePageByView(sortDirection, animeRepository.findByGenreIdOrderByAverageRatingsScoreDesc(genreId, pageable), animeRepository.findByGenreIdOrderByAverageRatingsScoreAsc(genreId, pageable));
        } else {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
            page = animeRepository.findByGenreId(genreId, pageable);
        }
        return page.map(dtoConverter::convertToDto);
    }

    private Page<Anime> getAnimePageByView(String sortDirection, Page<Anime> animeRepository, Page<Anime> animeRepository1) {
        if (sortDirection.equalsIgnoreCase("desc")) {
            return animeRepository;
        } else {
            return animeRepository1;
        }
    }


}
