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
import org.tolking.animeharbor.dto.RegisterDto;
import org.tolking.animeharbor.entities.enums.Provider;
import org.tolking.animeharbor.service.UserService;

import javax.management.relation.RoleNotFoundException;
import java.io.FileNotFoundException;

@Controller
@RequiredArgsConstructor
@PreAuthorize("isAnonymous()")
public class AuthController {
    private static final String LOGIN_VIEW = "/authorization/login";
    private static final String SIGN_UP_VIEW = "/authorization/signup";
    private static final String LOGIN_URL = "/login";
    private static final String REGISTER_URL = "/register";
    private static final String REGISTER_DTO_ATTR = "registerDto";


    private final UserService userService;


    @GetMapping(LOGIN_URL)
    public String loginPage() {
        return LOGIN_VIEW;
    }


    @GetMapping(REGISTER_URL)
    public String registerPage(Model model) {
        model.addAttribute(REGISTER_DTO_ATTR, new RegisterDto());
        return SIGN_UP_VIEW;
    }


    @PostMapping(REGISTER_URL)
    public String register(@ModelAttribute(REGISTER_DTO_ATTR) @Valid RegisterDto registerDto, BindingResult result) throws RoleNotFoundException, FileNotFoundException {
        if (checkIfErrorExists(registerDto, result)) {
            return SIGN_UP_VIEW;
        }

        userService.saveUser(registerDto, Provider.LOCAL);

        return "redirect:" + LOGIN_URL + "?created";
    }

    private boolean checkIfErrorExists(RegisterDto registerDto, BindingResult result) {
        boolean flag = result.hasErrors();
        if (userService.existsByEmail(registerDto.getEmail())) {
            result.rejectValue("email", "error.email", "Email already exists");
            flag = true;
        }
        if (userService.existsByUsername(registerDto.getUserName())) {
            result.rejectValue("userName", "error.username", "Username already exists");
            flag = true;
        }
        return flag;
    }
}
