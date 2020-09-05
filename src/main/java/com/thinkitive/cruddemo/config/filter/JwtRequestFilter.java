package com.thinkitive.cruddemo.config.filter;

import com.thinkitive.cruddemo.config.security.MyUserDetails;
import com.thinkitive.cruddemo.exception.InvalidTokenException;
import com.thinkitive.cruddemo.exception.ResourceNotFoundException;
import com.thinkitive.cruddemo.service.AuthService;
import com.thinkitive.cruddemo.shared.dto.UserDto;
import com.thinkitive.cruddemo.shared.properties.JwtProperties;
import com.thinkitive.cruddemo.shared.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final AuthService authService;
    private final JwtUtils jwtUtils;
    private final JwtProperties properties;
    private final HandlerExceptionResolver resolver;

    public JwtRequestFilter(AuthService authService, JwtUtils jwtUtils, JwtProperties properties, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.authService = authService;
        this.jwtUtils = jwtUtils;
        this.properties = properties;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(properties.getHeader());

        String userId = null;

        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String jwt = authorizationHeader.substring(7);

                userId = jwtUtils.extractSubject(jwt);
            }

            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDto userDto = this.authService.getUserByUserId(userId);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDto.getUserId(), null, new MyUserDetails(userDto).getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

            chain.doFilter(request, response);
        }
        // delegates exceptions to ExceptionHandler
        catch (ExpiredJwtException ex) {
            resolver.resolveException(request, response, null, new InvalidTokenException("token.expired", ""));
        } catch (SignatureException ex) {
            resolver.resolveException(request, response, null, new InvalidTokenException("invalid.token", ""));
        } catch (ResourceNotFoundException ex) {
            resolver.resolveException(request, response, null, ex);
        }

    }

}