package org.tolking.animeharbor.service.internal;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.tolking.animeharbor.entities.Anime;
import org.tolking.animeharbor.entities.repositories.AnimeRepository;
import org.tolking.animeharbor.service.AnimeService;

import java.util.List;

@Service
public class AnimeServiceImpl implements AnimeService {
    private final AnimeRepository animeRepository;

    public AnimeServiceImpl(AnimeRepository animeRepository) {
        this.animeRepository = animeRepository;
    }

    @Override
    public List<Anime> getAllAnimeWithPage(Pageable pageable) {
        return animeRepository.getAllBy(pageable);
    }
    @Override
    public List<Anime> getAllForRecentlyAddedPage() {
        Pageable pageable = PageRequest.of(0, 6, Sort.by("creation").descending());
        return getAllAnimeWithPage(pageable);
    }

    @Override
    public List<Anime> getAllForPopularityPage() {
        Pageable pageable  = PageRequest.of(0, 6);

        return animeRepository.getAllByPopularityForLast3Month(pageable);
    }

    @Override
    public List<Anime> getAllForTopViewPage() {
        Pageable pageable  = PageRequest.of(0, 6);

        return animeRepository.getAllByOrderByViewsDesc(pageable);
    }
}