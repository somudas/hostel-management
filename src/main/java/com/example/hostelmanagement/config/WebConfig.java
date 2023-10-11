package com.example.hostelmanagement.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((cs) -> cs.disable())
                .authorizeHttpRequests(
                (authz) -> authz
                        .anyRequest().anonymous()
        ).formLogin((formlogin) ->  formlogin.loginPage("/login").permitAll());
        return http.build();
    }

}
