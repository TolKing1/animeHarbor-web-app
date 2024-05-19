package org.tolking.animeharbor.service;

import org.springframework.data.domain.Page;
import org.tolking.animeharbor.dto.AnimeDTO;

import java.util.List;
import java.util.Optional;

public interface AnimeService {
    Optional<AnimeDTO> getAnimeById(long id);

    List<AnimeDTO> getAllAnime(int pageNo, int pageSize, String sortField, String sortDirection);
    List<AnimeDTO> getAllForPopularityPage();
    List<AnimeDTO> getAllForTopViewPage();
    List<AnimeDTO> getAllForRecentlyAddedPage();

    Page<AnimeDTO> searchAnime(String query, int pageNo, int pageSize, String sortField, String sortDirection);
    Page<AnimeDTO> getSortedAnimeDTOPageByGenre(long genreId, int pageNo, int pageSize, String sortField, String sortDirection);
}
