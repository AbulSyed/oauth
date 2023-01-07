package com.syed.rs.Resource.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new JWTRoleConverter());

        http.oauth2ResourceServer(
                config -> config.jwt(
                        jwt -> jwt.decoder(decoder())
                                .jwtAuthenticationConverter(jwtAuthenticationConverter)
                )
        );

        http.authorizeRequests().anyRequest().authenticated();
    }

    @Bean
    public JwtDecoder decoder() {
        String secret = "ffdhshgfjhgjhkjhkjhgjgffddsfdsgfdhgjhgkjlklklkjkjh";

        SecretKey key =
                new SecretKeySpec(secret.getBytes(), 0, secret.getBytes().length, "AES");

        return NimbusJwtDecoder
                .withSecretKey(key).build();
    }
}
