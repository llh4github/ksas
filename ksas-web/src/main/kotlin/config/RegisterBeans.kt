package io.github.llh4github.ksas.config

import io.github.llh4github.ksas.commons.IdGenerator
import io.github.llh4github.ksas.commons.IdGeneratorProperty
import io.github.llh4github.ksas.commons.IdGeneratorTrait
import io.github.llh4github.ksas.commons.property.JwtProperty
import org.babyfish.jimmer.sql.meta.DatabaseNamingStrategy
import org.babyfish.jimmer.sql.runtime.DefaultDatabaseNamingStrategy
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class RegisterBeans {

    @Bean
    @ConfigurationProperties(prefix = "ksas.jwt")
    fun jwt() = JwtProperty()

    @Bean
    @ConfigurationProperties(prefix = "ksas.id-generator")
    fun idGenerate() = IdGeneratorProperty()

    @Bean
    fun idGenerator(property: IdGeneratorProperty): IdGeneratorTrait {
        IdGenerator.configGenerator(property)
        return IdGenerator
    }

    @Bean
    fun databaseNamingStrategy(): DatabaseNamingStrategy =
        DefaultDatabaseNamingStrategy.LOWER_CASE

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}
