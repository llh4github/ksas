package io.github.llh4github.ksas.config

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.stereotype.Component

@Component
class SpringSecurityConfig(
    val property: WebSecurityProperty
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .logout { it.disable() }

        return http.build()
    }

    @Bean
    @Suppress("SpreadOperator")
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        val annoUrls = property.annoUrls.toTypedArray()
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring().requestMatchers(*annoUrls)
        }
    }
}
