package com.test.hex.cinehex.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            auth ->
                auth
                    // 1. Endpoints Públicos (Cartelera)
                    .requestMatchers(
                        "/api/v1/public/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html")
                    .permitAll()

                    // 2. Endpoints para el Microservicio de Negocio (Client Credentials)
                    // Solo alguien con el token 'ms-cinehex-business' y scope 'movies.admin' entra
                    // aquí
                    .requestMatchers("/api/v1/admin/**")
                    .hasAuthority("SCOPE_movies.admin")

                    // 3. Endpoints de Usuario (Frontend)
                    // Requieren scope de lectura y estar autenticados
                    .requestMatchers(HttpMethod.POST, "/api/v1/bookings/**")
                    .hasAuthority("SCOPE_cinehex.read")
                    .anyRequest()
                    .authenticated())
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

    return http.build();
  }
}
