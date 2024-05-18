package org.tolking.animeharbor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.tolking.animeharbor.exception.AnimeNotFoundException;
import org.tolking.animeharbor.service.CommentService;

import java.security.Principal;

import static org.tolking.animeharbor.constant.ControllerConstant.ANIME_URL;


@Controller
@RequiredArgsConstructor
public class CommentController {
    private static final String COMMENT_MESSAGE_ATTR = "commentedMessage";

    private final CommentService commentService;

    @PostMapping(ANIME_URL + "/comment")
    @PreAuthorize("isAuthenticated()")
    public String postComment(@RequestParam("comment") String comment,
                              @RequestParam("animeId") long animeId,
                              Principal principal,
                              RedirectAttributes redirectAttributes) throws AnimeNotFoundException {
        commentService.save(principal.getName(), animeId, comment);

        redirectAttributes.addFlashAttribute(COMMENT_MESSAGE_ATTR, true);

        return "redirect:%s/%d".formatted(ANIME_URL, animeId);
    }
}
