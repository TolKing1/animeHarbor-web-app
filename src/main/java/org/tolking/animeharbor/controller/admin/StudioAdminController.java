package org.tolking.animeharbor.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.tolking.animeharbor.dto.StudioDTO;
import org.tolking.animeharbor.service.StudioService;

import java.util.Optional;

import static org.tolking.animeharbor.constant.ControllerConstant.ADMIN_URL;

@Controller
@RequestMapping(ADMIN_URL)
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
public class StudioAdminController {
    public static final String STUDIO_ATTR = "studio";
    public static final String STUDIO_IS_EMPTY_ATTR = "isEmptyList";
    public static final String STUDIO_URL = "/studio";
    public static final String ADMIN_STUDIO_URL = "/admin/studio";
    private static final String STUDIO_VIEW = "admin/studio";
    private static final String STUDIO_DETAILS_VIEW = "admin/studio-details";
    private final StudioService studioService;

    @GetMapping(STUDIO_URL)
    public String getStudioPage(Model model) {
        model.addAttribute("studios", studioService.getAllStudios());
        return STUDIO_VIEW;
    }

    @GetMapping(STUDIO_URL + "/{id}")
    public String findById(@PathVariable("id") long id, Model model) {
        return getStudioById(id)
                .map(studio -> {
                    model.addAttribute(STUDIO_ATTR, studio);
                    model.addAttribute(STUDIO_IS_EMPTY_ATTR, studio.isEmptyAnimeList());
                    return STUDIO_DETAILS_VIEW;
                })
                .orElse("redirect:/error/404");
    }

    @PostMapping(STUDIO_URL + "/update")
    public String updateStudio(@ModelAttribute(STUDIO_ATTR) @Valid StudioDTO studioDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(STUDIO_ATTR, studioDTO);
            return STUDIO_DETAILS_VIEW;
        }
        studioService.save(studioDTO);
        return "redirect:" + studioDTO.getId();
    }

    @PostMapping(STUDIO_URL + "/delete")
    public String deleteStudio(@ModelAttribute(STUDIO_ATTR) StudioDTO studioDTO) {
        if (studioService.getStudioById(studioDTO.getId())
                .filter(StudioDTO::isEmptyAnimeList)
                .isPresent()) {
            studioService.delete(studioDTO);
        }
        return "redirect:" + ADMIN_STUDIO_URL;
    }

    private Optional<StudioDTO> getStudioById(long id) {
        return studioService.getStudioById(id);
    }
}
