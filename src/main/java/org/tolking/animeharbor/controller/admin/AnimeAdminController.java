package org.tolking.animeharbor.controller.admin;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.tolking.animeharbor.dto.anime.AnimeRegisterDTO;
import org.tolking.animeharbor.entities.enums.AnimeStatus;
import org.tolking.animeharbor.entities.enums.AnimeType;
import org.tolking.animeharbor.exception.InvalidMimeTypeException;
import org.tolking.animeharbor.service.AnimeService;
import org.tolking.animeharbor.service.GenreService;
import org.tolking.animeharbor.service.ImageService;
import org.tolking.animeharbor.service.StudioService;

import java.io.FileNotFoundException;

import static org.tolking.animeharbor.constant.ControllerConstant.ADMIN_ANIME_URL;

@Controller
@RequestMapping(ADMIN_ANIME_URL)
@MultipartConfig(maxFileSize = 1024*1024*10)
@RequiredArgsConstructor
public class AnimeAdminController {
    private static final String ADMIN_ANIME_VIEW = "admin/anime";
    private static final String ADMIN_ANIME_DETAILS_VIEW = "admin/anime-details";
    private static final String ANIME_LIST_ATTR = "animeList";
    private static final String ANIME_ATTR = "anime";
    private static final String ANIME_REGISTER_ATTR = "animeRegister";
    private static final String PICTURE_ERROR_ATTR = "pictureError";
    private final AnimeService animeService;
    private final GenreService genreService;
    private final StudioService studioService;
    private final ImageService imageService;

    @GetMapping
    public String getAnimePage(Model model) {
        model.addAttribute(ANIME_REGISTER_ATTR, new AnimeRegisterDTO());
        setup(model);
        appendAnimeListToModel(model);
        return ADMIN_ANIME_VIEW;
    }

    @PostMapping("/create")
    public String createAnime(@ModelAttribute(ANIME_REGISTER_ATTR) @Valid AnimeRegisterDTO animeDTO,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()){
            setup(model);
            appendAnimeListToModel(model);
            return ADMIN_ANIME_VIEW;
        }
        animeService.saveAnime(animeDTO);

        model.addAttribute(ANIME_REGISTER_ATTR, new AnimeRegisterDTO());
        setup(model);
        appendAnimeListToModel(model);

        return "redirect:"+ADMIN_ANIME_URL;
    }

    @PostMapping("/update")
    public String updateGenre(@RequestParam(name = "picture") MultipartFile multipartFile,
                              @ModelAttribute(ANIME_ATTR) @Valid AnimeRegisterDTO animeDTO,
                              BindingResult result,
                              Model model) throws IOFileUploadException, InvalidMimeTypeException {
        animeService.saveAnime(animeDTO);
        if (!multipartFile.isEmpty()) {
            imageService.saveAnimePic(multipartFile, animeDTO.getId());
        }
        setup(model);
        return "redirect:"+ADMIN_ANIME_URL+"/"+animeDTO.getId();
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") long id, Model model) {
        return animeService.getAnimeByIdForAdmin(id)
                .map(anime -> {
                    model.addAttribute(ANIME_ATTR, anime);
                    setup(model);
                    return ADMIN_ANIME_DETAILS_VIEW;
                })
                .orElse("redirect:/error/404");
    }

    @PostMapping("/delete")
    public String deleteGenre(@ModelAttribute(ANIME_ATTR) AnimeRegisterDTO animeDTO) {
        animeService.getAnimeById(animeDTO.getId())
                .ifPresent(anime -> animeService.deleteAnime(animeDTO));
        return "redirect:" + ADMIN_ANIME_URL;
    }
    private void setup(Model model) {
        model.addAttribute("statusArray", AnimeStatus.values());
        model.addAttribute("typeArray", AnimeType.values());
        model.addAttribute("genreList", genreService.getAllGenresOrderByTitle());
        model.addAttribute("studioList", studioService.getAllStudioName());
    }

    private void appendAnimeListToModel(Model model) {
        model.addAttribute(ANIME_LIST_ATTR, animeService.getAllForAdminPage());
    }

    @ExceptionHandler({InvalidMimeTypeException.class, FileNotFoundException.class})
    public String handleFileException(RedirectAttributes model, Exception e) {
        model.addFlashAttribute(PICTURE_ERROR_ATTR, e.getMessage());
        setup(model);
        return "redirect:"+ ADMIN_ANIME_URL+"/"+"2";
    }
}
