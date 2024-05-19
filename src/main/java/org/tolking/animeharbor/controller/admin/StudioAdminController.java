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

import static org.tolking.animeharbor.constant.ControllerConstant.ADMIN_STUDIO_URL;

@Controller
@RequestMapping(ADMIN_STUDIO_URL)
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
public class StudioAdminController {
    private static final String STUDIO_ATTR = "studio";
    private static final String STUDIO_IS_EMPTY_ATTR = "isEmptyList";
    private static final String STUDIO_REGISTER_ATTR = "studioRegister";
    private static final String STUDIOS_LIST_ATTR = "studios";
    private static final String STUDIO_VIEW = "admin/studio";
    private static final String STUDIO_DETAILS_VIEW = "admin/studio-details";

    private final StudioService studioService;

    @GetMapping
    public String getStudioPage(Model model) {
        model.addAttribute(STUDIO_REGISTER_ATTR, new StudioDTO());
        appendStudiosToModel(model);
        return STUDIO_VIEW;
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") long id, Model model) {
        return studioService.getStudioById(id)
                .map(studio -> {
                    model.addAttribute(STUDIO_ATTR, studio);
                    model.addAttribute(STUDIO_IS_EMPTY_ATTR, studio.isEmptyAnimeList());
                    return STUDIO_DETAILS_VIEW;
                })
                .orElse("redirect:/error/404");
    }

    @PostMapping("/update")
    public String updateStudio(@ModelAttribute(STUDIO_ATTR) @Valid StudioDTO studioDTO,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute(STUDIO_ATTR, studioDTO);
            return STUDIO_DETAILS_VIEW;
        }
        studioService.updateOrSave(studioDTO);
        return "redirect:" + studioDTO.getId();
    }

    @PostMapping("/create")
    public String createStudio(@ModelAttribute(STUDIO_REGISTER_ATTR) @Valid StudioDTO studioDTO,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute(STUDIO_REGISTER_ATTR, studioDTO);
            appendStudiosToModel(model);
            return STUDIO_VIEW;
        }
        studioService.updateOrSave(studioDTO);
        return "redirect:" + ADMIN_STUDIO_URL;
    }


    @PostMapping("/delete")
    public String deleteStudio(@ModelAttribute(STUDIO_ATTR) StudioDTO studioDTO) {
        if (studioService.getStudioById(studioDTO.getId())
                .filter(StudioDTO::isEmptyAnimeList)
                .isPresent()) {
            studioService.delete(studioDTO);
        }
        return "redirect:" + ADMIN_STUDIO_URL;
    }

    private void appendStudiosToModel(Model model) {
        model.addAttribute(STUDIOS_LIST_ATTR, studioService.getAllStudios());
    }
}
