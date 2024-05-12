package org.tolking.animeharbor.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.tolking.animeharbor.entities.Anime;
import org.tolking.animeharbor.entities.Genre;
import org.tolking.animeharbor.service.AnimeService;
import org.tolking.animeharbor.service.internal.GenreServiceImpl;

import java.util.Optional;

@Controller
@RequestMapping("/genres")
public class GenreController {
    private static final String GENRE_VIEW = "genre";
    private static final String GENRE_URL = "/genres";
    private static final String GENRE_SELECTED_VIEW = "genre-selected";

    private static final String GENRES_ATTR = "genres";
    private static final String GENRE_ID_ATTR = "genreId";
    private static final String GENRE_TITLE_ATTR = "genreTitle";
    private static final String ANIME_VIEWS_ATTR = "animeByView";
    private static final String ANIME_LIST_ATTR = "animeList";
    private static final String SORT_BY_ATTR = "sortBy";
    private static final String SORT_DIRECTION_ATTR = "sortDirection";

    private final GenreServiceImpl genreServiceImpl;
    private final AnimeService animeService;

    private final int pageSize = 6;

    public GenreController(GenreServiceImpl genreServiceImpl, AnimeService animeService) {
        this.genreServiceImpl = genreServiceImpl;
        this.animeService = animeService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute(GENRES_ATTR, genreServiceImpl.getAllGenres());
        return GENRE_VIEW;
    }


    @GetMapping(value = "/{id}")
    public String getAllByGenre(
            @PathVariable("id") long id,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "creation") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            Model model) {
        Optional<Genre> genre = genreServiceImpl.getByGenre(id);
        if (genre.isPresent() && !genre.get().isEmpty()) {
            Genre g = genre.get();
            Page<Anime> animeList = animeService.getSortedAnimePageByGenre(id, pageNo, pageSize, sortBy, sortDirection);

            model.addAttribute(GENRE_ID_ATTR, g.getId());
            model.addAttribute(GENRE_TITLE_ATTR, g.getTitle());
            model.addAttribute(ANIME_LIST_ATTR, animeList);
            model.addAttribute(ANIME_VIEWS_ATTR, animeService.getAllForTopViewPage());

            model.addAttribute(SORT_BY_ATTR, sortBy);
            model.addAttribute(SORT_DIRECTION_ATTR, sortDirection);
            return GENRE_SELECTED_VIEW;
        } else {
            return "redirect:" + GENRE_URL;
        }

    }


}
