package org.tolking.animeharbor.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tolking.animeharbor.dto.PasswordDto;
import org.tolking.animeharbor.entities.User;
import org.tolking.animeharbor.service.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@RequestMapping("/me")
public class ProfileController {
    private static final String ME_VIEW = "profile";
    public static final String USERNAME_ATTR = "username";
    public static final String EMAIL_ATTR = "email";
    public static final String PASSWORD_ATTR = "password";
    public static final String IMG_ATTR = "imagePath";
    private final UserService userService;

    @GetMapping
    public String me(Model model, Principal principal){
        model.addAttribute(PASSWORD_ATTR, new PasswordDto());
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

        model.addAttribute(PASSWORD_ATTR, new PasswordDto());
        return setModelAttributesForProfile(principal, model);
    }

    private String setModelAttributesForProfile(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());

        model.addAttribute(USERNAME_ATTR, user.getUsername());
        model.addAttribute(EMAIL_ATTR, user.getEmail());
        model.addAttribute(IMG_ATTR, user.getPicture());
        return ME_VIEW;
    }


}
