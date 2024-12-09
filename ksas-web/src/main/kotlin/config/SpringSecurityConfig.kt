package io.github.llh4github.ksas.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.llh4github.ksas.commons.JsonWrapper
import io.github.llh4github.ksas.commons.property.WebSecurityProperty
import io.github.llh4github.ksas.filter.JwtFilter
import io.github.llh4github.ksas.library.JwtService
import io.github.llh4github.ksas.service.auth.UserService
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.RegexRequestMatcher
import org.springframework.stereotype.Component

@Component
class SpringSecurityConfig(
    private val property: WebSecurityProperty,
    private val jwtService: JwtService,
    private val objectMapper: ObjectMapper,
    private val userService: UserService,
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
                    .requestMatchers(RegexRequestMatcher("^.*\\.(css|js)$", null)).permitAll()
//                    .anyRequest().access { authentication, context ->
//                        val bo = EndpointPermCheckBo(
//                            username = authentication.get().principal as String,
//                            method = context.request.method,
//                            uri = context.request.requestURI,
//                        )
//                        val hasPerm = userService.endpointPermCheck(bo)
//                        AuthorizationDecision(hasPerm)
//                    }
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

}


internal fun jsonAccessDeniedHandler(mapper: ObjectMapper): AccessDeniedHandler {
    val json = JsonWrapper<Void>(msg = "无权访问", code = "ACCESS_DENIED", module = "AUTH")
    return AccessDeniedHandler { _, response, _ ->
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        response.status = 200
        response.writer.write(mapper.writeValueAsString(json))
    }
}

internal fun jsonAuthenticationEntryPoint(mapper: ObjectMapper): AuthenticationEntryPoint {
    val json = JsonWrapper<Void>(msg = "未登录", code = "NOT_LOGIN", module = "AUTH")
    return AuthenticationEntryPoint { _, response, _ ->
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        response.status = 200
        response.writer.write(mapper.writeValueAsString(json))
    }
}
