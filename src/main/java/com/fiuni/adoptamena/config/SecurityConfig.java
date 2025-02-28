package com.fiuni.adoptamena.config;

import com.fiuni.adoptamena.exception_handler.CustomAccessDeniedHandler;
import com.fiuni.adoptamena.exception_handler.CustomAuthenticationEntryPoint;
import com.fiuni.adoptamena.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// Otras importaciones...

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        @Autowired
        private JwtAuthenticationFilter jwtAuthenticationFilter;

        @Autowired
        private AuthenticationProvider authProvider;

        @Autowired
        private CustomAccessDeniedHandler accessDeniedHandler;

        @Autowired
        private CustomAuthenticationEntryPoint authenticationEntryPoint;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .csrf(AbstractHttpConfigurer::disable)
                                .cors(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(authRequest -> authRequest
                                .requestMatchers(
                                    "/auth/**",                      // Permitir endpoints de autenticación
                                    "/swagger-ui/**",                // Permitir Swagger UI
                                    "/swagger-ui.html",              // Página principal de Swagger
                                    "/v3/api-docs/**",               // Documentación OpenAPI
                                    "/v3/api-docs/swagger-config",   // Configuración de Swagger
                                    "/webjars/**"                     // Recursos estáticos de Swagger
                                ).permitAll()
                                .anyRequest().authenticated())
                                .exceptionHandling(exceptions -> exceptions
                                                .authenticationEntryPoint(authenticationEntryPoint)
                                                .accessDeniedHandler(accessDeniedHandler))
                                .sessionManagement(sessionManager -> sessionManager
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authProvider)
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                                .build();
        }
}
