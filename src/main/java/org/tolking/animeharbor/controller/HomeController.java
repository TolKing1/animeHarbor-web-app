package org.tolking.animeharbor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.tolking.animeharbor.service.AnimeService;

@Controller
public class HomeController {
    private final AnimeService animeService;

    public HomeController(AnimeService animeService) {
        this.animeService = animeService;
    }
    @GetMapping({"/", ""})
    public String home(Model model) {
        model.addAttribute("animeByPopular",animeService.getAllForPopularityPage());
        model.addAttribute("animeByView",animeService.getAllForTopViewPage());
        model.addAttribute("animeByInsertDate",animeService.getAllForRecentlyAddedPage());
        return "index";
    }

}
