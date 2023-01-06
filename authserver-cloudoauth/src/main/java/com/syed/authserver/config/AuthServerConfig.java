package com.syed.authserver.config;

import com.syed.authserver.security.ClientDetailsServiceImpl;
import com.syed.authserver.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final CustomJwtTokenEnhancer customJwtTokenEnhancer;
    private final UserDetailsServiceImpl userDetailsService;
    private final ClientDetailsServiceImpl clientDetailsService;

    public AuthServerConfig(AuthenticationManager authenticationManager,
                            CustomJwtTokenEnhancer customJwtTokenEnhancer,
                            UserDetailsServiceImpl userDetailsService,
                            ClientDetailsServiceImpl clientDetailsService) {
        this.authenticationManager = authenticationManager;
        this.customJwtTokenEnhancer = customJwtTokenEnhancer;
        this.userDetailsService = userDetailsService;
        this.clientDetailsService = clientDetailsService;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // enabling the /oauth/token_key endpoint
        //  security.tokenKeyAccess("isAuthenticated()");
//        security.passwordEncoder(new BCryptPasswordEncoder());
        security.passwordEncoder(NoOpPasswordEncoder.getInstance());
        /*
         * GET req + clientId&secret
         * http://localhost:8080/oauth/token_key
         */
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                    // authorization_code grant type
//                    .withClient("client1")
//                    .secret(new BCryptPasswordEncoder().encode("secret1"))
//                    .scopes("read", "write")
//                    .authorizedGrantTypes("authorization_code")
//                    .redirectUris("http://localhost:9090");

        clients.inMemory()
                // authorization_code grant type
                .withClient("client1")
                .secret("secret1")
                .scopes("read", "write")
                .authorizedGrantTypes("authorization_code")
                .redirectUris("http://localhost:9090");

//        clients.withClientDetails(clientDetailsService);

        // 1. get authorization_code
        // http://localhost:8080/oauth/authorize?response_type=code&client_id=client1&scope=read write
        // 2. get access_token by sending authorization_code to auth server
        /*
         *  > POST (scope is optional?)
         *  http://localhost:8080/oauth/token?grant_type=authorization_code&scope=read&code={authorization_code}
         *   + (basic auth)
         *  {
         *       username: client1,
         *       password: secret1
         *   }
        */
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();

        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(customJwtTokenEnhancer, jwtAccessTokenConverter()));

        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancerChain)
                .userDetailsService(userDetailsService);
                 // .accessTokenConverter(jwtAccessTokenConverter()); // prior to tokenEnhancer
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    // configure our JWT (signing)
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("ffdhshgfjhgjhkjhkjhgjgffddsfdsgfdhgjhgkjlklklkjkjh");
        return converter;
    }
}
