package com.openclassrooms.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class AuthenticationConfig {

    private final SpringSecurityConfig springSecurityConfig;

    // Constructor injection pour SpringSecurityConfig
    public AuthenticationConfig(SpringSecurityConfig springSecurityConfig) {
        this.springSecurityConfig = springSecurityConfig;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        // Crée un AuthenticationManager à partir de HttpSecurity sans utiliser "and()"
        AuthenticationManagerBuilder authenticationManagerBuilder = 
                http.getSharedObject(AuthenticationManagerBuilder.class);
        
        authenticationManagerBuilder
                .userDetailsService(springSecurityConfig.userDetailsService()) // Utilise la méthode de SpringSecurityConfig
                .passwordEncoder(springSecurityConfig.passwordEncoder()); // Utilise la méthode de SpringSecurityConfig

        return authenticationManagerBuilder.build(); // Renvoie le AuthenticationManager
    }

    // @Bean
    // public AuthenticationManager authManager(HttpSecurity http) throws Exception {
    //     return http.getSharedObject(AuthenticationManagerBuilder.class)
    //             .userDetailsService(userDetailsService())
    //             .passwordEncoder(passwordEncoder())
    //             .and()
    //             .build();
    // }
    // @Bean
    // public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService) throws Exception {
    //     AuthenticationManagerBuilder authenticationManagerBuilder =
    //         http.getSharedObject(AuthenticationManagerBuilder.class);
    //     authenticationManagerBuilder.userDetailsService(userDetailsService)
    //         .passwordEncoder(bCryptPasswordEncoder);
    //     return authenticationManagerBuilder.build();
    // }
}

