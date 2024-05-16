package org.tolking.animeharbor.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.tolking.animeharbor.dto.RegisterDto;
import org.tolking.animeharbor.entities.enums.Provider;
import org.tolking.animeharbor.security.CustomOAuth2User;
import org.tolking.animeharbor.service.UserService;

import java.util.UUID;

@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final UserService userService;

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail(oauthUser.getEmail());
        registerDto.setUserName(oauthUser.getName());
        registerDto.setPassword(UUID.randomUUID().toString());

        userService.saveUser(registerDto, Provider.GOOGLE);

        response.sendRedirect("/?login");
    }
}
