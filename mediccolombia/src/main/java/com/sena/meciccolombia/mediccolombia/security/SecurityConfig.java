package com.sena.meciccolombia.mediccolombia.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
       try{
         http
            .csrf( csrf -> csrf.disable())
            .authorizeHttpRequests( auth -> auth
                .anyRequest().permitAll()
            );
        return http.build();
       } catch (Exception e){
        throw new IllegalStateException("Error al configurar la seguridad de la aplicacion", e);
       }
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    
}