package org.tolking.animeharbor.service;

import org.springframework.data.domain.Page;
import org.tolking.animeharbor.entities.Anime;

import java.util.List;

public interface AnimeService {
    List<Anime> getAllAnime(int pageNo, int pageSize, String sortField, String sortDirection);
    Page<Anime> getAllAnimeByGenreId(long id, int pageNo, int pageSize, String sortField, String sortDirection);
    List<Anime> getAllForPopularityPage();
    List<Anime> getAllForTopViewPage();
    List<Anime> getAllForRecentlyAddedPage();

    Page<Anime> searchAnime(String query, int pageNo, int pageSize, String sortField, String sortDirection);
    Page<Anime> getSortedAnimePageByGenre(long genreId, int pageNo, int pageSize, String sortField, String sortDirection);
}
