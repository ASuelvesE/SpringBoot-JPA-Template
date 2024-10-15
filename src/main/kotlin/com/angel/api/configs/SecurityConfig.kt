package com.angel.api.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable() // Deshabilitar CSRF si no es necesario
            .authorizeHttpRequests { authorizeRequests ->
                authorizeRequests
                    // Permitir acceso público a las rutas de Swagger
                    .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/apiv1/auth/**"
                    ).permitAll()
                    // Permitir acceso a todas las demás solicitudes
                    .anyRequest().permitAll()
            }

        return http.build()
    }
}
