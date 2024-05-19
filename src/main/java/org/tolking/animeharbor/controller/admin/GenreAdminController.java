package org.tolking.animeharbor.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.tolking.animeharbor.dto.genre.GenreNameDTO;
import org.tolking.animeharbor.service.GenreService;

import static org.tolking.animeharbor.constant.ControllerConstant.ADMIN_GENRE_URL;

@Controller
@RequestMapping(ADMIN_GENRE_URL)
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
public class GenreAdminController {
    private static final String GENRE_ATTR = "genre";
    private static final String GENRE_IS_EMPTY_ATTR = "isEmptyList";
    private static final String GENRE_REGISTER_ATTR = "genreRegister";
    private static final String GENRE_LIST_ATTR = "genres";
    private static final String GENRE_VIEW = "admin/genre";
    private static final String GENRE_DETAILS_VIEW = "admin/genre-details";

    private final GenreService genreService;

    @GetMapping
    public String getGenrePage(Model model) {
        model.addAttribute(GENRE_REGISTER_ATTR, new GenreNameDTO());
        appendStudiosToModel(model);
        return GENRE_VIEW;
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") long id, Model model) {
        return genreService.getByGenre(id)
                .map(genre -> {
                    model.addAttribute(GENRE_ATTR, genre);
                    model.addAttribute(GENRE_IS_EMPTY_ATTR, genre.isEmpty());
                    return GENRE_DETAILS_VIEW;
                })
                .orElse("redirect:/error/404");
    }

    @PostMapping("/update")
    public String updateGenre(@ModelAttribute(GENRE_ATTR) @Valid GenreNameDTO genreNameDTO,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute(GENRE_ATTR, genreNameDTO);
            return GENRE_DETAILS_VIEW;
        }
        genreService.save(genreNameDTO);
        return "redirect:" + genreNameDTO.getId();
    }

    @PostMapping("/create")
    public String createGenre(@ModelAttribute(GENRE_REGISTER_ATTR) @Valid GenreNameDTO genreNameDTO,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute(GENRE_REGISTER_ATTR, genreNameDTO);
            appendStudiosToModel(model);
            return GENRE_VIEW;
        }
        genreService.save(genreNameDTO);
        return "redirect:" + ADMIN_GENRE_URL;
    }


    @PostMapping("/delete")
    public String deleteGenre(@ModelAttribute(GENRE_ATTR) GenreNameDTO genreNameDTO) {
        if (genreService.getByGenre(genreNameDTO.getId())
                .filter(GenreNameDTO::isEmpty)
                .isPresent()) {
            genreService.delete(genreNameDTO);
        }
        return "redirect:" + ADMIN_GENRE_URL;
    }

    private void appendStudiosToModel(Model model) {
        model.addAttribute(GENRE_LIST_ATTR, genreService.getAllGenre());
    }
}
