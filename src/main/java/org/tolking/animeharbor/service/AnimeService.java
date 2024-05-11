package org.tolking.animeharbor.service;

import org.springframework.data.domain.Pageable;
import org.tolking.animeharbor.entities.Anime;

import java.util.List;

public interface AnimeService {
    List<Anime> getAllAnimeWithPage(Pageable pageable);
    List<Anime> getAllForPopularityPage();
    List<Anime> getAllForTopViewPage();
    List<Anime> getAllForRecentlyAddedPage();
}
