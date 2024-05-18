package org.tolking.animeharbor.service.internal;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.tolking.animeharbor.dto.RegisterDto;
import org.tolking.animeharbor.entities.User;
import org.tolking.animeharbor.entities.enums.Provider;
import org.tolking.animeharbor.exception.UserNotFoundException;
import org.tolking.animeharbor.security.CustomOAuth2User;
import org.tolking.animeharbor.service.CustomOAuth2UserService;
import org.tolking.animeharbor.service.UserService;

import javax.management.relation.RoleNotFoundException;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.UUID;

import static org.tolking.animeharbor.service.internal.UserServiceImpl.mapAuthorities;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserServiceImpl extends DefaultOAuth2UserService implements CustomOAuth2UserService {

    private final UserService userService;

    @SneakyThrows
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        return findOrRegisterUser(oAuth2User, email, name);
    }

    private OAuth2User findOrRegisterUser(OAuth2User oAuth2User, String email, String name) throws FileNotFoundException, RoleNotFoundException, UserNotFoundException {
        Optional<User> optionalUser = userService.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.isEnabled()) {
                return createCustomOAuth2User(oAuth2User, user);
            } else {
                throw new InternalAuthenticationServiceException("User is disabled");
            }
        } else {
            User newUser = saveUser(email, name);
            return createCustomOAuth2User(oAuth2User, newUser);
        }
    }

    private CustomOAuth2User createCustomOAuth2User(OAuth2User oAuth2User, User user) throws UserNotFoundException {
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return new CustomOAuth2User(oAuth2User, mapAuthorities(user.getRoles()));
    }

    private User saveUser(String email, String name) throws FileNotFoundException, RoleNotFoundException {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail(email);
        registerDto.setUserName(name);
        registerDto.setPassword(UUID.randomUUID().toString());

        return userService.saveUser(registerDto, Provider.GOOGLE).orElseThrow(() -> new InternalAuthenticationServiceException("Failed to save user"));
    }
}
