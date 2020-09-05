package com.thinkitive.cruddemo.ui.controller;

import com.thinkitive.cruddemo.service.AuthService;
import com.thinkitive.cruddemo.shared.dto.UserDto;
import com.thinkitive.cruddemo.shared.utils.JwtUtils;
import com.thinkitive.cruddemo.ui.request.UserRequest;
import com.thinkitive.cruddemo.ui.resopnse.UserLoginResponse;
import com.thinkitive.cruddemo.ui.resopnse.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/auth")
@Validated
@Slf4j
public class AuthController {

    private final ModelMapper modelMapper;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtTokenUtil;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtUtils jwtTokenUtil) {
        modelMapper = new ModelMapper();
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody @Valid UserRequest userRequest) throws BadCredentialsException {

        // authenticate user
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userRequest.getEmail(),
                userRequest.getPassword(),
                new ArrayList<>()
        ));

        UserDto userDto = authService.getUserByEmail(userRequest.getEmail());

        log.info("UserLogin -- login.user -- userId={}", userDto.getUserId());

        //send jwt token back to user
        String jwtToken = jwtTokenUtil.generateToken(userDto.getUserId());
        return ResponseEntity.ok(new UserLoginResponse(jwtToken));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) {
        // map UserRequest to UserDto -> save user to db -> map to UserResponse -> send it to client
        UserDto userDto = modelMapper.map(userRequest, UserDto.class);
        UserDto storedUserDetails = authService.createUser(userDto);

        log.info("UserRegister -- create.user -- userId={}", storedUserDetails.getUserId());
        //send stored user details as response
        UserResponse returnValue = modelMapper.map(storedUserDetails, UserResponse.class);
        return new ResponseEntity<>(returnValue, HttpStatus.CREATED);
    }

    @GetMapping
    public String test() {
        return "test";
    }

}
