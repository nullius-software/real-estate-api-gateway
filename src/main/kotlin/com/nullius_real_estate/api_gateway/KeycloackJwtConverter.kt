package com.nullius_real_estate.api_gateway

import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import reactor.core.publisher.Mono
import java.util.stream.Collectors.toSet
import java.util.stream.Stream

class KeycloackJwtConverter : Converter<Jwt, Mono<AbstractAuthenticationToken>> {
    override fun convert(source: Jwt): Mono<AbstractAuthenticationToken> {
        val authorities = Stream.concat(
            JwtGrantedAuthoritiesConverter().convert(source)?.stream() ?: Stream.empty(),
            extractResourcesRoles(source).stream()
        ).collect(toSet())

        return Mono.just(
            JwtAuthenticationToken(
                source,
                authorities,
                source.subject
            )
        )
    }


    private fun extractResourcesRoles(jwt: Jwt): Collection<GrantedAuthority> {
        val resourceAccess = jwt.getClaimAsMap("resource_access") ?: return emptySet()

        val allRoles = resourceAccess.values
            .asSequence()
            .filterIsInstance<Map<*, *>>()
            .flatMap { it["roles"] as? Collection<*> ?: emptyList() }
            .filterNotNull()
            .map { "ROLE_${it.toString().replace("-", "_")}" }
            .map { SimpleGrantedAuthority(it) }
            .toList()

        return allRoles.toSet()
    }
}
