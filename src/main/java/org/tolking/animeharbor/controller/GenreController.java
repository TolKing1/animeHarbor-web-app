package org.tolking.animeharbor.controller;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class GenreController {
    private static final String GENRE_VIEW = "genre";
    private static final String GENRE_URL = "/genres";
    private static final String GENRE_SELECTED_VIEW = "genre-selected";

    private static final String GENRES_ATTR = "genres";
    private static final String GENRE_ID_ATTR = "genreId";
    private static final String GENRE_TITLE_ATTR = "genreTitle";
    private static final String ANIME_VIEWS_ATTR = "animeByView";
    private static final String ANIME_PAGE_ATTR = "animePage";
    private static final String SORT_BY_ATTR = "sortBy";
    private static final String SORT_DIRECTION_ATTR = "sortDirection";

    private static final String FORM_NAME_ATTR = "formName";
    private static final String FORM_NAME = "paginate";

    private final GenreServiceImpl genreServiceImpl;
    private final AnimeService animeService;

    private final int pageSize = 9;

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
            Page<Anime> animePage = animeService.getSortedAnimePageByGenre(id, pageNo, pageSize, sortBy, sortDirection);

            model.addAttribute(GENRE_ID_ATTR, g.getId());
            model.addAttribute(GENRE_TITLE_ATTR, g.getTitle());
            model.addAttribute(ANIME_PAGE_ATTR, animePage);
            model.addAttribute(ANIME_VIEWS_ATTR, animeService.getAllForTopViewPage());

            model.addAttribute(SORT_BY_ATTR, sortBy);
            model.addAttribute(SORT_DIRECTION_ATTR, sortDirection);

            model.addAttribute(FORM_NAME_ATTR, FORM_NAME);
            return GENRE_SELECTED_VIEW;
        } else {
            return "redirect:" + GENRE_URL;
        }

    }


}
