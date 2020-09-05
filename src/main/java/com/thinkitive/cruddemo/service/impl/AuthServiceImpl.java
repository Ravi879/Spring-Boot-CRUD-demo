package com.thinkitive.cruddemo.service.impl;

import com.thinkitive.cruddemo.config.security.MyUserDetails;
import com.thinkitive.cruddemo.exception.ResourceAlreadyExistsException;
import com.thinkitive.cruddemo.exception.ResourceNotFoundException;
import com.thinkitive.cruddemo.repository.entity.RoleEntity;
import com.thinkitive.cruddemo.repository.entity.UserEntity;
import com.thinkitive.cruddemo.repository.entity.UserRepository;
import com.thinkitive.cruddemo.service.AuthService;
import com.thinkitive.cruddemo.shared.dto.UserDto;
import com.thinkitive.cruddemo.shared.utils.JwtUtils;
import com.thinkitive.cruddemo.shared.utils.RandomIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtils jwtUtils;

    public AuthServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtils jwtUtils) {
        mapper = new ModelMapper();
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        //check id email address already register
        if (userRepository.findByEmail(userDto.getEmail()).isPresent())
            throw new ResourceAlreadyExistsException("email.registered", "email=" + userDto.getEmail());

        // populate dto properties before inserting to database
        userDto.setUserId(RandomIdUtils.getId());
        userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        Set<RoleEntity> roles = new HashSet<>();
        if (userDto.getEmail().contains("admin"))
            roles.add(new RoleEntity("ADMIN"));
        else
            roles.add(new RoleEntity("USER"));
        userDto.setRoles(roles);

        //store details to database
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        UserEntity storedUserEntity = userRepository.save(userEntity);
        return mapper.map(storedUserEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userEntity -> mapper.map(userEntity, UserDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("email.not.found", "email=" + email));
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .map(userEntity -> mapper.map(userEntity, UserDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("invalid.userId", "userId=" + userId));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDto userDto = getUserByEmail(email);
        return new MyUserDetails(userDto);
    }

}

