package org.tolking.animeharbor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.tolking.animeharbor.exception.AnimeNotFoundException;
import org.tolking.animeharbor.service.RatingService;

import java.security.Principal;

import static org.tolking.animeharbor.controller.AnimeDetailControllers.ANIME_DETAILS_URL;

@Controller
@RequiredArgsConstructor
public class RatingController {
    private static final String RATED_MESSAGE_ATTR = "ratedMessage";

    private final RatingService ratingService;

    @PostMapping(ANIME_DETAILS_URL + "/{id}/rate")
    @PreAuthorize("isAuthenticated()")
    public String rateAnime(@RequestParam("score") int score,
                            @PathVariable("id") int animeId,
                            Principal principal,
                            RedirectAttributes redirectAttributes) throws AnimeNotFoundException {
        ratingService.save(principal.getName(), animeId, score);

        redirectAttributes.addFlashAttribute(RATED_MESSAGE_ATTR, true);

        return "redirect:%s/%d".formatted(ANIME_DETAILS_URL, animeId);
    }
}
