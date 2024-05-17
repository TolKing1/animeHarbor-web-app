package org.tolking.animeharbor.service.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tolking.animeharbor.entities.Anime;
import org.tolking.animeharbor.entities.Views;
import org.tolking.animeharbor.repositories.AnimeRepository;
import org.tolking.animeharbor.repositories.ViewsRepository;
import org.tolking.animeharbor.service.ViewService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ViewServiceImpl implements ViewService {
    private final ViewsRepository viewsRepository;
    private final AnimeRepository animeRepository;

    public void createView(long animeId) {
        Optional<Anime> anime = animeRepository.findById(animeId);

        if (anime.isPresent()) {
            Views views = new Views();
            views.setAnime(anime.get());

            viewsRepository.save(views);
        }
    }
}
