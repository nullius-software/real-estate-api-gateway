package com.nullius_real_estate.api_gateway

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(httpSecurity: ServerHttpSecurity): SecurityWebFilterChain {
        httpSecurity
            .csrf { it.disable() }
            .cors(Customizer.withDefaults())
            .authorizeExchange {
                it.pathMatchers(
//                    Auth
                    "/api/auth/register",
                    "/api/auth/login",
                    "/api/auth/user/{keycloakUserId}/is-verified",

//                    Docs
                    "/webjars/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**",

//                    User Docs
                    "/api/user/webjars/**",
                    "/api/user/swagger-ui/**",
                    "/api/user/v3/**",

//                    Auth Docs
                    "/api/auth/webjars/**",
                    "/api/auth/swagger-ui/**",
                    "/api/auth/v3/**",

//                    Estate Docs
                    "/api/estate/webjars/**",
                    "/api/estate/swagger-ui/**",
                    "/api/estate/v3/**"
                ).permitAll()
                it.anyExchange().authenticated()
            }
            .oauth2ResourceServer {
                it.jwt { jwt ->
                    jwt.jwtAuthenticationConverter(keycloakJwtConverter())
                }
            }

        return httpSecurity.build()
    }

    @Bean
    fun keycloakJwtConverter(): KeycloackJwtConverter {
        return KeycloackJwtConverter()
    }
}