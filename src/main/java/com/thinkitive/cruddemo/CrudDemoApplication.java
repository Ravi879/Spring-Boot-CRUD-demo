package com.thinkitive.cruddemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
@EnableOAuth2Sso
public class CrudDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudDemoApplication.class, args);
    }

    // credits:
    // https://stackoverflow.com/questions/35875098/protecting-rest-api-with-oauth2-error-creating-bean-with-name-scopedtarget-oau
    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }
}
