package com.syed.rs.Resource.server.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JWTRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        // get authorities & superpowers
        List<String> authorities = (List<String>) source.getClaims().get("authorities");
        List<String> superpowers = (List<String>) source.getClaims().get("superpowers");

        List<String> newList = Stream.concat(authorities.stream(), superpowers.stream())
                .collect(Collectors.toList());

        if (newList == null || newList.isEmpty()) {
            return new ArrayList<>();
        }

        return newList
                .stream().map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
