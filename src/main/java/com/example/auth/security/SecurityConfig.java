package com.example.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //le indica al contenedor de spring que esta es una clase de seguridad al momento de arrancar la aplicación
@EnableWebSecurity //se activa la seguridad web en nuestra aplicación
public class SecurityConfig {
    private JwtAuthenticationEntryPoint entryPoint;

    @Autowired
    public SecurityConfig(JwtAuthenticationEntryPoint entryPoint) {
        this.entryPoint = entryPoint;
    }

    //Este bean se encarga de verificar la información de los usuarios que se loguearan en la API
    @Bean
    AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
