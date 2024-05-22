package org.tolking.animeharbor.service;

import org.springframework.data.domain.Page;
import org.tolking.animeharbor.dto.anime.AnimeAdminPageDTO;
import org.tolking.animeharbor.dto.anime.AnimeDTO;
import org.tolking.animeharbor.dto.anime.AnimeRegisterDTO;

import java.util.List;
import java.util.Optional;

public interface AnimeService {
    Optional<AnimeDTO> getAnimeById(long id);

    Optional<AnimeRegisterDTO> getAnimeByIdForAdmin(long id);

    void saveAnime(AnimeRegisterDTO animeDTO);

    void deleteAnime(AnimeRegisterDTO animeDTO);

    List<AnimeAdminPageDTO> getAllForAdminPage();

    List<AnimeDTO> getAllAnime(int pageNo, int pageSize, String sortField, String sortDirection);

    List<AnimeDTO> getAllForPopularityPage();

    List<AnimeDTO> getAllForHeroPage();

    List<AnimeDTO> getAllForTopViewPage();

    List<AnimeDTO> getAllForRecentlyAddedPage();

    Page<AnimeDTO> searchAnime(String query, int pageNo, int pageSize, String sortField, String sortDirection);

    Page<AnimeDTO> getSortedAnimeDTOPageByGenre(long genreId, int pageNo, int pageSize, String sortField, String sortDirection);
}
