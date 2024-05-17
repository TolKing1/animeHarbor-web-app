package org.tolking.animeharbor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tolking.animeharbor.entities.Anime;
import org.tolking.animeharbor.service.AnimeService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/anime")
public class AnimeDetailControllers {
    private static final String ANIME_VIEW = "anime-details";
    private static final String NOT_FOUND_VIEW = "error/404";

    private static final String ANIME_ATTR = "anime";
    private final AnimeService animeService;

    @GetMapping("/{id}")
    public String getAnime(@PathVariable int id, Model model){
        Optional<Anime> animeOptional = animeService.getAnimeById(id);

        if (animeOptional.isEmpty()) {
            return NOT_FOUND_VIEW;
        }else {
            Anime anime = animeOptional.get();
            model.addAttribute(ANIME_ATTR, anime);

            return ANIME_VIEW;
        }

    }
}
