package com.thinkitive.cruddemo.config.security;

import com.thinkitive.cruddemo.repository.entity.RoleEntity;
import com.thinkitive.cruddemo.shared.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class MyUserDetails implements UserDetails {

    private UserDto userDto;

    public MyUserDetails(UserDto userDto) {
        this.userDto = userDto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<RoleEntity> roles = userDto.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (RoleEntity role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return userDto.getEncryptedPassword();
    }

    @Override
    public String getUsername() {
        return userDto.getEmail();
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
}
