package com.thinkitive.cruddemo.config.security;

import com.thinkitive.cruddemo.config.filter.JwtRequestFilter;
import com.thinkitive.cruddemo.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthService authService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(AuthService authService, BCryptPasswordEncoder bCryptPasswordEncoder, JwtRequestFilter jwtRequestFilter) {
        this.authService = authService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/auth/login", "/auth/register").permitAll()
                .antMatchers(HttpMethod.GET, "/patients").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/patients").hasAnyAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                // specifying that no session should be created, due to this, it prevents Authorization header from being cached, otherwise it caching the authorization header.
                // we want each rest api call to authorize again with token and not to create session and cached it.
                // this makes rest api stateless.
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // add filter for authenticate incoming request
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // set UserDetailsService implementation and password encryption strategy
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // set password encoder and custom UserDetailsService
        auth.userDetailsService(authService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}