package org.tolking.animeharbor.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tolking.animeharbor.dto.anime.AnimeRegisterDTO;
import org.tolking.animeharbor.entities.enums.AnimeStatus;
import org.tolking.animeharbor.entities.enums.AnimeType;
import org.tolking.animeharbor.service.AnimeService;
import org.tolking.animeharbor.service.GenreService;
import org.tolking.animeharbor.service.ImageService;
import org.tolking.animeharbor.service.StudioService;

import static org.tolking.animeharbor.constant.ControllerConstant.ADMIN_ANIME_URL;

@Controller
@RequestMapping(ADMIN_ANIME_URL)
@RequiredArgsConstructor
public class AnimeAdminController {
    private static final String ADMIN_ANIME_VIEW = "admin/anime";
    private static final String ANIME_LIST_ATTR = "animeList";
    private static final String ANIME_REGISTER_ATTR = "animeRegister";
    private final AnimeService animeService;
    private final GenreService genreService;
    private final StudioService studioService;
    private final ImageService imageService;


    @GetMapping
    public String getAnimePage(Model model) {
        model.addAttribute(ANIME_REGISTER_ATTR, new AnimeRegisterDTO());
        setup(model);
        return ADMIN_ANIME_VIEW;
    }

    @PostMapping("/create")
    public String createAnime(@ModelAttribute(ANIME_REGISTER_ATTR) @Valid AnimeRegisterDTO animeDTO,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()){
            setup(model);
            return ADMIN_ANIME_VIEW;
        }
        model.addAttribute(ANIME_REGISTER_ATTR, new AnimeRegisterDTO());
        setup(model);
        animeService.saveAnime(animeDTO);
        return "redirect:"+ADMIN_ANIME_URL;
    }

    private void setup(Model model) {
        model.addAttribute("statusArray", AnimeStatus.values());
        model.addAttribute("typeArray", AnimeType.values());
        model.addAttribute("genreList", genreService.getAllGenresOrderByTitle());
        model.addAttribute("studioList", studioService.getAllStudioName());
        appendAnimeListToModel(model);
    }

    private void appendAnimeListToModel(Model model) {
        model.addAttribute(ANIME_LIST_ATTR, animeService.getAllForAdminPage());
    }
}
