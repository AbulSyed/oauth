package com.syed.authserver.security;//package com.syed.authserver.security;

import com.syed.authserver.entity.Superpower;
import com.syed.authserver.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SecurityUser implements UserDetails {

    private final User user;

    public SecurityUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public Collection<? extends GrantedAuthority> getSuperpowers() {
        Set<Superpower> superpowers = new HashSet<>();
        superpowers.add(new Superpower("fly"));
        superpowers.add(new Superpower("invisible"));
        superpowers.add(new Superpower("firepunch"));
        return superpowers.stream().map(superpower -> new SimpleGrantedAuthority(superpower.getName())).collect(Collectors.toList());
    }

    public Long getId() {
        return user.getId();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "SecurityUser{" +
                "user=" + user +
                '}';
    }
}
