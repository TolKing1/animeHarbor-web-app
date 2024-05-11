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
    public static final String GENRE_VIEW = "genre";
    public static final String GENRE_URL = "/genres";
    public static final String GENRE_SELECTED_VIEW = "genre-selected";
    public static final String GENRES_ATTR = "genres";
    public static final String GENRE_ID_ATTR = "genreId";
    public static final String GENRE_TITLE_ATTR = "genreTitle";
    public static final String ANIME_VIEWS_ATTR = "animeByView";
    public static final String ANIME_LIST_ATTR = "animeList";
    private final GenreServiceImpl genreServiceImpl;
    private final AnimeService animeService;

    public GenreController(GenreServiceImpl genreServiceImpl, AnimeService animeService) {
        this.genreServiceImpl = genreServiceImpl;
        this.animeService = animeService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute(GENRES_ATTR, genreServiceImpl.getAllGenres());
        return GENRE_VIEW;
    }


    @RequestMapping(value = "/{id}",method = {RequestMethod.GET,RequestMethod.POST})
    public String getAllByGenre(
            @PathVariable("id") long id,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "6") int pageSize,
            @RequestParam(defaultValue = "creation") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            Model model) {
        Optional<Genre> genre = genreServiceImpl.getByGenre(id);
        if (genre.isPresent() && !genre.get().isEmpty()) {
            Genre g = genre.get();
            Page<Anime> animeList = animeService.getSortedAnimePageByGenre(id, pageNo - 1, pageSize, sortBy, sortDirection);

            model.addAttribute(GENRE_ID_ATTR, g.getId());
            model.addAttribute(GENRE_TITLE_ATTR, g.getTitle());
            model.addAttribute(ANIME_LIST_ATTR, animeList.get());
            model.addAttribute(ANIME_VIEWS_ATTR, animeService.getAllForTopViewPage());
            return GENRE_SELECTED_VIEW;
        } else {
            return "redirect:" + GENRE_URL;
        }

    }


}
