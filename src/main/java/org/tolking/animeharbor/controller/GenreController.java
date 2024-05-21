package org.tolking.animeharbor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tolking.animeharbor.dto.anime.AnimeDTO;
import org.tolking.animeharbor.dto.genre.GenreNameDTO;
import org.tolking.animeharbor.service.AnimeService;
import org.tolking.animeharbor.service.GenreService;

import java.util.Optional;

import static org.tolking.animeharbor.constant.ControllerConstant.GENRE_URL;

@Controller
@RequestMapping(GENRE_URL)
@RequiredArgsConstructor
public class GenreController {
    private static final String GENRE_VIEW = "genre";

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

    private final GenreService genreService;
    private final AnimeService animeService;

    private final int pageSize = 9;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute(GENRES_ATTR, genreService.getAllGenresOrderByTitle());
        return GENRE_VIEW;
    }


    @GetMapping(value = "/{id}")
    public String getAllByGenre(
            @PathVariable("id") long id,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "created") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            Model model) {
        Optional<GenreNameDTO> genre = genreService.getByGenre(id);
        if (genre.isPresent() && !genre.get().isEmpty()) {
            GenreNameDTO g = genre.get();
            Page<AnimeDTO> animePage = animeService.getSortedAnimeDTOPageByGenre(id, pageNo, pageSize, sortBy, sortDirection);

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
