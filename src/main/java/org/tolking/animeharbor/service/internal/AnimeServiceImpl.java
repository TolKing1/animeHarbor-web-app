package org.tolking.animeharbor.service.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.tolking.animeharbor.entities.Anime;
import org.tolking.animeharbor.repositories.AnimeRepository;
import org.tolking.animeharbor.service.AnimeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeServiceImpl implements AnimeService {
    private final AnimeRepository animeRepository;

    @Override
    public List<Anime> getAllAnime(int pageNo, int pageSize, String sortField, String sortDirection) {
        Pageable pageable = getPageable(pageNo, pageSize, sortField, sortDirection);
        return animeRepository.getAllBy(pageable);
    }

    @Override
    public Page<Anime> searchAnime(String query, int pageNo, int pageSize, String sortField, String sortDirection) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        if (sortField.equalsIgnoreCase("views")){
            if (sortDirection.equalsIgnoreCase("desc")) {
                return animeRepository.searchFullTextOrderByCountViewsDesc(query, pageable);
            } else {
                return animeRepository.searchFullTextOrderByCountViewsAsc(query, pageable);
            }
        } else if (sortField.equalsIgnoreCase("rating")) {
            if (sortDirection.equalsIgnoreCase("desc")) {
                return animeRepository.searchFullTextOrderByAverageRatingScoreDesc(query, pageable);
            } else {
                return animeRepository.searchFullTextOrderByAverageRatingScoreAsc(query, pageable);
            }
        }else {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
            return animeRepository.searchFullText(query, pageable);
        }
    }

    @Override
    public List<Anime> getAllForRecentlyAddedPage() {
        return getAllAnime(0, 6, "creation", "desc");
    }


    @Override
    public Page<Anime> getAllAnimeByGenreId(long id, int pageNo, int pageSize, String sortField, String sortDirection) {
        Pageable pageable = getPageable(pageNo, pageSize, sortField, sortDirection);
        return animeRepository.findByGenreId(id, pageable);
    }

    @Override
    public List<Anime> getAllForPopularityPage() {
        Pageable pageable = PageRequest.of(0, 6);

        return animeRepository.getAllByPopularityForLast3Month(pageable);
    }

    @Override
    public List<Anime> getAllForTopViewPage() {
        return animeRepository.getAllByOrderByViews(PageRequest.of(0, 6));
    }

    @Override
    public Page<Anime> getSortedAnimePageByGenre(long genreId, int pageNo, int pageSize, String sortField, String sortDirection) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        if (sortField.equalsIgnoreCase("views")){
            if (sortDirection.equalsIgnoreCase("desc")) {
                return animeRepository.findByGenreIdOrderByCountViewsDesc(genreId, pageable);
            } else {
                return animeRepository.findByGenreIdOrderByCountViewsAsc(genreId, pageable);
            }
        } else if (sortField.equalsIgnoreCase("rating")) {
            if (sortDirection.equalsIgnoreCase("desc")) {
                return animeRepository.findByGenreIdOrderByAverageRatingsScoreDesc(genreId, pageable);
            } else {
                return animeRepository.findByGenreIdOrderByAverageRatingsScoreAsc(genreId, pageable);
            }
        }else {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
            return animeRepository.findByGenreId(genreId, pageable);
        }
    }

    private static Pageable getPageable(int pageNo, int pageSize, String sortField, String sortDirection) {
        return PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
    }



}
