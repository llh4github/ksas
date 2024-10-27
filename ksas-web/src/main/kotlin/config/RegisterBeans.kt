package io.github.llh4github.ksas.config

import io.github.llh4github.ksas.commons.IdGenerator
import io.github.llh4github.ksas.commons.IdGeneratorProperty
import io.github.llh4github.ksas.commons.IdGeneratorTrait
import io.github.llh4github.ksas.commons.property.JwtProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
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

}
