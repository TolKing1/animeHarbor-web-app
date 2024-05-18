package org.tolking.animeharbor.service.internal;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.tolking.animeharbor.repositories.UserRepository;
import org.tolking.animeharbor.service.CustomOAuth2UserService;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserServiceImpl extends DefaultOAuth2UserService implements CustomOAuth2UserService {
    private final UserRepository userRepository;

    @SneakyThrows
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User user = super.loadUser(userRequest);
        String email = user.getAttribute("email");
        if (userRepository.existsByEmail(email)){
            if (userRepository.existsByUsernameAndEnabledIsTrue(email)){
                return user;
            }else {
                throw new InternalAuthenticationServiceException("User is disabled");
            }
        }else {
            return user;
        }
    }
}
