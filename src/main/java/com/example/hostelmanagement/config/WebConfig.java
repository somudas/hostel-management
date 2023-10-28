package com.example.hostelmanagement.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static jakarta.servlet.DispatcherType.ERROR;
import static jakarta.servlet.DispatcherType.FORWARD;


@EnableWebSecurity
@Configuration
public class WebConfig {

    private final DataSource dataSource;
    @Autowired
    public WebConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from users where username=?")
                .authoritiesByUsernameQuery("select username, authority from authorities where username=?")
                .passwordEncoder(passwordEncoder());
//                .passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(cs -> cs.disable())
                .authorizeHttpRequests(
                (authz) -> authz
                        .dispatcherTypeMatchers(FORWARD, ERROR).permitAll()
                        .requestMatchers("/assets/**", "/index..html", "/", "/logout", "/login", "/register", "/error").permitAll()
                        .requestMatchers("/student", "/chats/**","/api/**", "/establish-socket-connection", "/socket/**", "/complaints/**", "/dues/**", "/profile/**").hasAnyAuthority("student", "warden","staff")
                        .requestMatchers("/services/**","/update/**").hasAnyAuthority("staff","warden")
                        .requestMatchers("/inventory/**").hasAnyAuthority("warden")
                        .requestMatchers("/api/create/group").permitAll()
//                        .anyRequest().denyAll()
        ).formLogin((formlogin) ->  formlogin.loginPage("/login").permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll())
        ;

        return http.build();
    }


}
