package org.tolking.animeharbor.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tolking.animeharbor.service.UserService;

import static org.tolking.animeharbor.constant.ControllerConstant.ADMIN_USER_URL;

@Controller
@RequestMapping(ADMIN_USER_URL)
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
public class UserAdminController {
    private static final String USER_VIEW = "admin/user";
    private static final String USERS_ATTR = "users";
    private final UserService userService;

    @GetMapping
    public String getUserPage(Model model) {
        model.addAttribute(USERS_ATTR, userService.getUsers());
        return USER_VIEW;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/update/{id}")
    public String updateToAdmin(@PathVariable Long id) {
        userService.enableAdminRole(id);
        return "redirect:" + ADMIN_USER_URL;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/degrade/{id}")
    public String degradeFromAdmin(@PathVariable Long id) {
        userService.disableAdminRole(id);
        return "redirect:" + ADMIN_USER_URL;
    }

    @GetMapping("/enable/{id}")
    public String enableUser(@PathVariable Long id, Authentication authentication) {
        userService.enableUser(id, authentication);
        return "redirect:" + ADMIN_USER_URL;
    }

    @GetMapping("/disable/{id}")
    public String disableUser(@PathVariable Long id, Authentication authentication) {
        userService.disableUser(id, authentication);
        return "redirect:" + ADMIN_USER_URL;
    }
}
