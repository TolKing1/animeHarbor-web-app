package org.tolking.animeharbor.controller;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.tolking.animeharbor.dto.PasswordDto;
import org.tolking.animeharbor.entities.User;
import org.tolking.animeharbor.exception.InvalidMimeTypeException;
import org.tolking.animeharbor.service.ImageService;
import org.tolking.animeharbor.service.UserService;

import java.io.FileNotFoundException;
import java.security.Principal;

import static org.tolking.animeharbor.constant.ControllerConstant.PROFILE_URL;

@Controller
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@MultipartConfig(maxFileSize = 1024*1024*5)
@RequestMapping(PROFILE_URL)
public class ProfileController {
    public static final String PROFILE_VIEW = "profile";

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
        if (errorBinding(newPassword, result, principal, model)) return setModelAttributesForProfile(principal, model);

        addPassword(model);
        return setModelAttributesForProfile(principal, model);
    }

    @PostMapping("/picture")
    public ModelAndView updateProfilePicture(@RequestParam("picture") MultipartFile multipartFile,
                                       ModelMap model,
                                       Principal principal) throws Exception {
        imageService.saveProfile(multipartFile, principal.getName());

        return new ModelAndView("redirect:"+ PROFILE_URL, model);
    }

    private String setModelAttributesForProfile(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());

        model.addAttribute(USERNAME_ATTR, user.getUsername());
        model.addAttribute(EMAIL_ATTR, user.getEmail());
        model.addAttribute(IMG_ATTR, user.getImage().getId());
        return PROFILE_VIEW;
    }

    private boolean errorBinding(PasswordDto newPassword, BindingResult result, Principal principal, Model model) {
        if (result.hasErrors()) {
            return true;
        } else if (!newPassword.getPassword().equals(newPassword.getConfirmPassword())) {
            result.rejectValue("password","error.password", "Passwords are not the same");
            return true;
        } else {
            userService.updateUserPassword(principal.getName(), newPassword);
        }
        return false;
    }

    private static void addPassword(Model model) {
        model.addAttribute(PASSWORD_ATTR, new PasswordDto());
    }


    @ExceptionHandler({InvalidMimeTypeException.class, FileNotFoundException.class})
    public String handleFileException(RedirectAttributes model, Principal principal, Exception e) {
        model.addFlashAttribute(PICTURE_ERROR_ATTR, e.getMessage());
        addPassword(model);
        return "redirect:"+ PROFILE_URL;
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(MaxUploadSizeExceededException exc, RedirectAttributes attributes) {
        attributes.addFlashAttribute(PICTURE_ERROR_ATTR, exc.getMessage());
        return "redirect:"+ PROFILE_URL;
    }

}
