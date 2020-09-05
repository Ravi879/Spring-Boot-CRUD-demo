package com.thinkitive.cruddemo.shared.dto;

import com.thinkitive.cruddemo.repository.entity.RoleEntity;

import java.util.HashSet;
import java.util.Set;

public class UserDto {

    private static final long serialVersionUID = 2261979261626808122L;

    private Long id;
    private String userId;
    private String email;
    private String password;
    private String encryptedPassword;
    private Set<RoleEntity> roles = new HashSet<>();

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", encryptedPassword='" + encryptedPassword +
                '}';
    }
}

