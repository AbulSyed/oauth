package com.syed.authserver.config;

import com.syed.authserver.security.SecurityUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomJwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken,
                                     OAuth2Authentication oAuth2Authentication) {

        SecurityUser user = (SecurityUser) oAuth2Authentication.getPrincipal();

        List<String> superpowers = user.getSuperpowers().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(Collections.singletonMap("superpowers", superpowers));
        return oAuth2AccessToken;
    }
}
