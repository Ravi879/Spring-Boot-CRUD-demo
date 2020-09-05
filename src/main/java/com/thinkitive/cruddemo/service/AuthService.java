package com.thinkitive.cruddemo.service;

import com.thinkitive.cruddemo.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    UserDto createUser(UserDto userDto);

    UserDto getUserByEmail(String email);

    UserDto getUserByUserId(String userId);

}
