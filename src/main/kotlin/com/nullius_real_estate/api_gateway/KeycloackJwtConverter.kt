package com.nullius_real_estate.api_gateway

import org.springframework.core.convert.converter.Converter
import org.springframework.lang.NonNull
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import java.util.stream.Collectors.toSet
import java.util.stream.Stream

class KeycloackJwtConverter : Converter<Jwt, AbstractAuthenticationToken> {
    override fun convert(@NonNull source: Jwt): AbstractAuthenticationToken {
        return JwtAuthenticationToken(
            source,
            Stream.concat(
                JwtGrantedAuthoritiesConverter().convert(source)!!.stream(),
                extractResourcesRoles(source).stream()
            ).collect(
                toSet()
            ),
            source.subject
        )
    }


    private fun extractResourcesRoles(jwt: Jwt): Collection<GrantedAuthority> {
        val resourceAccess = mutableMapOf<String, Any>(jwt.getClaim("resource_access"))

        val accountAccess = resourceAccess["account"] as Map<*, *>
        val roles = accountAccess["roles"] as List<*>
        return roles.stream()
            .map { role ->
                SimpleGrantedAuthority("ROLE_" + role.toString().replace("-", "_"))
            }.collect(toSet())
    }
}
