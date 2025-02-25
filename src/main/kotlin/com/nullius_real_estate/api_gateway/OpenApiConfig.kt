package com.nullius_real_estate.api_gateway

import org.springdoc.core.models.GroupedOpenApi
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig(private val discoveryClient: DiscoveryClient) {

    @Bean
    fun authOpenApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("auth-microservice")
            .pathsToMatch("/api/auth/**")
            .build()
    }

    @Bean
    fun userOpenApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("user-microservice")
            .pathsToMatch("/api/user/**")
            .build()
    }

    @Bean
    fun estateOpenApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("estate-microservice")
            .pathsToMatch("/api/estate/**")
            .build()
    }
}