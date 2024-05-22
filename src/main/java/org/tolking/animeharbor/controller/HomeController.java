package org.tolking.animeharbor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.tolking.animeharbor.service.AnimeService;

import static org.tolking.animeharbor.constant.ControllerConstant.CONTACTS_URL;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final AnimeService animeService;

    @GetMapping({"/", ""})
    public String home(Model model) {
        model.addAttribute("animeByPopular",animeService.getAllForPopularityPage());
        model.addAttribute("animeByView",animeService.getAllForTopViewPage());
        model.addAttribute("animeByInsertDate",animeService.getAllForRecentlyAddedPage());
        model.addAttribute("animeHero",animeService.getAllForHeroPage());
        return "index";
    }

    @GetMapping(CONTACTS_URL)
    public String contact() {
        return "contact";
    }

}
