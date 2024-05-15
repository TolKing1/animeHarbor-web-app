package org.tolking.animeharbor.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.tolking.animeharbor.dto.RegisterDto;
import org.tolking.animeharbor.entities.enums.Provider;
import org.tolking.animeharbor.exception.UserAlreadyExists;
import org.tolking.animeharbor.service.UserService;

import javax.management.relation.RoleNotFoundException;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    private final static String LOGIN_VIEW = "login";
    private final static String LOGIN_URL = "/login";
    private final static String SIGN_UP_VIEW = "signup";
    private final static String REGISTER_DTO_ATTR = "registerDto";


    @GetMapping("/login")
    public String loginPage(Model model) {
        return LOGIN_VIEW;
    }


    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute(REGISTER_DTO_ATTR, new RegisterDto());
        return SIGN_UP_VIEW;
    }


    @PostMapping("/register")
    public String register(@ModelAttribute(REGISTER_DTO_ATTR) @Valid RegisterDto registerDto, BindingResult result) throws RoleNotFoundException, UserAlreadyExists {
        if (checkIfErrorExists(registerDto, result)) {
            return SIGN_UP_VIEW;
        }

        userService.saveUser(registerDto, Provider.LOCAL);

        return "redirect:"+LOGIN_URL+"?created";
    }

    private boolean checkIfErrorExists(RegisterDto registerDto, BindingResult result) {
        boolean flag = result.hasErrors();
        if (userService.existsByEmail(registerDto.getEmail())) {
            result.rejectValue("email","error.email","Email already exists");
            flag = true;
        }
        if (userService.existsByUsername(registerDto.getUserName())){
            result.rejectValue("userName","error.username", "Username already exists");
            flag = true;
        }
        return flag;
    }
}
