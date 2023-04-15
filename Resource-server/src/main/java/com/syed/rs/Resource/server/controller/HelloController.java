package com.syed.rs.Resource.server.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hi")
    public String hi(@AuthenticationPrincipal Jwt jwt) {
        System.out.println(jwt.getClaims());
        return "hi";
    }

    @GetMapping("/hello")
    @PreAuthorize("hasRole('firepunch')")
    public String hello(@AuthenticationPrincipal Jwt jwt) {
        System.out.println(jwt.getClaims());
        return "hello";
    }
}
