package org.tolking.animeharbor.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;


public class CustomOAuth2User implements OAuth2User {
    private final OAuth2User oauth2User;
    private final Collection<GrantedAuthority> authorities;

    public CustomOAuth2User(OAuth2User oauth2User, Collection<GrantedAuthority> authorities) {
        this.oauth2User = oauth2User;

        authorities.addAll(oauth2User.getAuthorities());
        this.authorities = authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return oauth2User.getAttribute("name");
    }

    public String getEmail() {
        return oauth2User.getAttribute("email");
    }
}
