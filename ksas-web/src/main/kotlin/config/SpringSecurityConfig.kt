package io.github.llh4github.ksas.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.llh4github.ksas.commons.JsonWrapper
import io.github.llh4github.ksas.commons.property.WebSecurityProperty
import io.github.llh4github.ksas.filter.JwtFilter
import io.github.llh4github.ksas.library.JwtService
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component

@Component
class SpringSecurityConfig(
    private val property: WebSecurityProperty,
    private val jwtService: JwtService,
    private val objectMapper: ObjectMapper,
) {

    @Bean
    @Suppress("SpreadOperator")
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val annoUrls = property.anonUrls.toTypedArray()
        http.csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .logout { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(*annoUrls).permitAll()
                    .anyRequest().authenticated()
            }
            .exceptionHandling {
                it.accessDeniedHandler(jsonAccessDeniedHandler(objectMapper))
                it.authenticationEntryPoint(jsonAuthenticationEntryPoint(objectMapper))
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(
                JwtFilter(property, jwtService),
                UsernamePasswordAuthenticationFilter::class.java
            )

        return http.build()
    }

//    @Bean
//    fun webSecurityCustomizer(): WebSecurityCustomizer {
//        val annoUrls = property.anonUrls.toTypedArray()
//        return WebSecurityCustomizer { web ->
//            web.ignoring().requestMatchers(*annoUrls)
//        }
//    }
}


internal fun jsonAccessDeniedHandler(mapper: ObjectMapper): AccessDeniedHandler {
    val json = JsonWrapper<Void>(msg = "用户无权访问", code = "ACCESS_DENIED")
    return AccessDeniedHandler { _, response, _ ->
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        response.status = 200
        response.writer.write(mapper.writeValueAsString(json))
    }
}

internal fun jsonAuthenticationEntryPoint(mapper: ObjectMapper): AuthenticationEntryPoint {
    val json = JsonWrapper<Void>(msg = "用户无权访问", code = "ACCESS_DENIED")
    return AuthenticationEntryPoint { _, response, _ ->
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        response.status = 200
        response.writer.write(mapper.writeValueAsString(json))
    }
}
