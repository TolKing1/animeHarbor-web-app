package org.tolking.animeharbor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tolking.animeharbor.entities.Anime;
import org.tolking.animeharbor.service.AnimeService;

import static org.tolking.animeharbor.constant.ControllerConstant.SEARCH_URL;

@Controller
@RequestMapping(SEARCH_URL)
@RequiredArgsConstructor
public class SearchController {

    private final AnimeService animeService;

    private static final String SEARCH_VIEW = "search";

    private static final String QUERY_ATTR = "query";
    private static final String ANIME_PAGE_ATTR = "animePage";
    private static final String SORT_BY_ATTR = "sortBy";
    private static final String SORT_DIRECTION_ATTR = "sortDirection";

    private static final String FORM_NAME_ATTR = "formName";
    private static final String FORM_NAME = "paginate";

    private final int pageSize = 12;

    @GetMapping
    public String search(@RequestParam(defaultValue = "") String query,
                         @RequestParam(defaultValue = "0") int pageNo,
                         @RequestParam(defaultValue = "creation") String sortBy,
                         @RequestParam(defaultValue = "desc") String sortDirection,
                         Model model) {
        Page<Anime> animePage = animeService.searchAnime(query,pageNo,pageSize,sortBy,sortDirection);

        model.addAttribute(ANIME_PAGE_ATTR, animePage);

        model.addAttribute(QUERY_ATTR, query);
        model.addAttribute(SORT_BY_ATTR, sortBy);
        model.addAttribute(SORT_DIRECTION_ATTR, sortDirection);

        model.addAttribute(FORM_NAME_ATTR, FORM_NAME);

        return SEARCH_VIEW;
    }
}
