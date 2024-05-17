package org.tolking.animeharbor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tolking.animeharbor.entities.Anime;
import org.tolking.animeharbor.service.WatchListService;

import java.security.Principal;
import java.util.List;

import static org.tolking.animeharbor.controller.AnimeDetailControllers.ANIME_DETAILS_URL;

@Controller
@RequiredArgsConstructor
@RequestMapping("/watchlist")
@PreAuthorize("isAuthenticated()")
public class WatchListController {
    public static final String WATCHLIST_VIEW = "watchlist";
    public static final String WATCHLIST = "watchlist";
    private final WatchListService watchListService;

    @GetMapping
    public String getWatchList(Model model, Principal principal) {
        List<Anime> animeList = watchListService.getList(principal.getName());

        model.addAttribute(WATCHLIST, animeList);
        return WATCHLIST_VIEW;
    }


    @GetMapping("/add")
    public String addWatchList(@RequestParam("animeId") long animeId,
                               Principal principal) {
        watchListService.addToList(animeId, principal.getName());

        return "redirect:%s/%d".formatted(ANIME_DETAILS_URL, animeId);
    }

    @GetMapping("/remove")
    public String removeWatchList(@RequestParam("animeId") long animeId,
                               Principal principal) {
        watchListService.removeFromList(animeId, principal.getName());

        return "redirect:%s/%d".formatted(ANIME_DETAILS_URL, animeId);
    }
}
