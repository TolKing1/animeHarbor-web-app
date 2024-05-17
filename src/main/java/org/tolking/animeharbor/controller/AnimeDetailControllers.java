package org.tolking.animeharbor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tolking.animeharbor.entities.Anime;
import org.tolking.animeharbor.entities.Comment;
import org.tolking.animeharbor.service.AnimeService;
import org.tolking.animeharbor.service.CommentService;
import org.tolking.animeharbor.service.ViewService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/anime")
public class AnimeDetailControllers {
    public static final String ANIME_DETAILS_URL = "/anime";
    private static final String ANIME_VIEW = "anime-details";
    private static final String NOT_FOUND_VIEW = "error/404";

    private static final String ANIME_ATTR = "anime";
    private static final String COMMENTS_ATTR = "comments";
    private static final String ANIME_LIST_BY_POPULARITY_ATTR = "animeListByPopularity";

    private final AnimeService animeService;
    private final CommentService commentService;
    private final ViewService viewService;

    @GetMapping("/{id}")
    public String getAnime(@PathVariable int id, Model model) {
        Optional<Anime> animeOptional = animeService.getAnimeById(id);
        if (animeOptional.isEmpty()) {
            return NOT_FOUND_VIEW;
        } else {
            Anime anime = animeOptional.get();

            viewService.createView(anime.getId());

            List<Anime> animeListByPopularity = animeService.getAllForPopularityPage();
            List<Comment> commentList = commentService.getLast5Comments(anime.getId());

            model.addAttribute(ANIME_ATTR, anime);
            model.addAttribute(ANIME_LIST_BY_POPULARITY_ATTR, animeListByPopularity);
            model.addAttribute(COMMENTS_ATTR, commentList);
            return ANIME_VIEW;
        }
    }


}
