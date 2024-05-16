package org.tolking.animeharbor.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.tolking.animeharbor.dto.PasswordDto;
import org.tolking.animeharbor.entities.User;
import org.tolking.animeharbor.exception.InvalidMimeTypeException;
import org.tolking.animeharbor.service.ImageService;
import org.tolking.animeharbor.service.UserService;

import java.io.FileNotFoundException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@RequestMapping("/me")
public class ProfileController {
    public static final String ME_VIEW = "profile";
    public static final String ME_URL = "/me";
    public static final String USERNAME_ATTR = "username";
    public static final String EMAIL_ATTR = "email";
    public static final String PASSWORD_ATTR = "password";
    public static final String PICTURE_ERROR_ATTR = "pictureErr";
    public static final String IMG_ATTR = "imageId";

    private final UserService userService;
    private final ImageService imageService;

    @GetMapping
    public String me(Model model, Principal principal){
        addPassword(model);
        return setModelAttributesForProfile(principal, model);

    }

    @PostMapping("/password")
    public String updateProfilePassword(@ModelAttribute("password") @Valid PasswordDto newPassword,
                                        BindingResult result,
                                        Principal principal,
                                        Model model) {
        if (result.hasErrors()) {
            return setModelAttributesForProfile(principal, model);
        } else if (!newPassword.getPassword().equals(newPassword.getConfirmPassword())) {
            result.rejectValue("password","error.password", "Passwords are not the same");
            return setModelAttributesForProfile(principal, model);
        } else {
            userService.updateUserPassword(principal.getName(), newPassword);
        }

        addPassword(model);
        return setModelAttributesForProfile(principal, model);
    }


    @PostMapping("/picture")
    public ModelAndView updateProfilePicture(@RequestParam("picture") MultipartFile multipartFile,
                                       ModelMap model,
                                       Principal principal) throws Exception {
        imageService.saveProfile(multipartFile, principal.getName());

        return new ModelAndView("redirect:"+ME_URL, model);
    }

    private String setModelAttributesForProfile(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());

        model.addAttribute(USERNAME_ATTR, user.getUsername());
        model.addAttribute(EMAIL_ATTR, user.getEmail());
        model.addAttribute(IMG_ATTR, user.getImage().getId());
        return ME_VIEW;
    }

    private static void addPassword(Model model) {
        model.addAttribute(PASSWORD_ATTR, new PasswordDto());
    }


    @ExceptionHandler({InvalidMimeTypeException.class, FileNotFoundException.class})
    public String handleFileException(Model model, Principal principal, Exception e) {
        model.addAttribute(PICTURE_ERROR_ATTR, e.getMessage());
        addPassword(model);
        return setModelAttributesForProfile(principal, model);
    }

}
