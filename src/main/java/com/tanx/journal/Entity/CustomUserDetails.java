package com.tanx.journal.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    @Autowired
    private final UserEntry userEntry;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> roles = userEntry.getRoles();
        // If your UserEntry has roles, replace this:
//         return userEntry.getRoles().stream().map(SimpleGrantedAuthority::new).toList();
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // e.g. "USER" -> "ROLE_USER"
                .collect(Collectors.toList());
//        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return userEntry.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntry.getUsername();
    }
}