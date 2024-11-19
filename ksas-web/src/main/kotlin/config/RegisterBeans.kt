package io.github.llh4github.ksas.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.llh4github.ksas.commons.IdGenerator
import io.github.llh4github.ksas.commons.IdGeneratorProperty
import io.github.llh4github.ksas.commons.LongIdGenerator
import io.github.llh4github.ksas.commons.property.WebSecurityProperty
import org.babyfish.jimmer.meta.ImmutableProp
import org.babyfish.jimmer.meta.ImmutableType
import org.babyfish.jimmer.sql.cache.Cache
import org.babyfish.jimmer.sql.cache.CacheFactory
import org.babyfish.jimmer.sql.cache.redis.spring.RedisCacheCreator
import org.babyfish.jimmer.sql.kt.cache.AbstractKCacheFactory
import org.babyfish.jimmer.sql.meta.DatabaseNamingStrategy
import org.babyfish.jimmer.sql.runtime.DefaultDatabaseNamingStrategy
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RegisterBeans {

    @Bean
    @ConfigurationProperties(prefix = "ksas.security")
    fun securityProperty() = WebSecurityProperty()

    @Bean
    @ConfigurationProperties(prefix = "ksas.id-generator")
    fun idGenerate() = IdGeneratorProperty()

    @Bean
    fun idGenerator(property: IdGeneratorProperty): LongIdGenerator {
        IdGenerator.configGenerator(property)
        return IdGenerator
    }

    @Bean
    fun databaseNamingStrategy(): DatabaseNamingStrategy =
        DefaultDatabaseNamingStrategy.LOWER_CASE

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()


    @Bean
    fun cacheFactory(
        connectionFactory: RedisConnectionFactory,
        objectMapper: ObjectMapper
    ): CacheFactory {
        val creator = RedisCacheCreator(connectionFactory, objectMapper)
            .withRemoteDuration(Duration.ofHours(1))
            .withLocalCache(100, Duration.ofMinutes(5))
            .withMultiViewProperties(40, Duration.ofMinutes(2), Duration.ofMinutes(24))
//            .withSoftLock( // Optional, for better consistency
//                RedissonCacheLocker(redissonClient),
//                Duration.ofSeconds(30)
//            )
        return object : AbstractKCacheFactory() {

            // Id -> Object
            override fun createObjectCache(type: ImmutableType): Cache<*, *>? =
                creator.createForObject<Any, Any>(type)

            // Id -> TargetId, for one-to-one/many-to-one
            override fun createAssociatedIdCache(prop: ImmutableProp): Cache<*, *>? =
                creator.createForProp<Any, Any>(prop, filterState.isAffected(prop.targetType))

            // Id -> TargetId list, for one-to-many/many-to-many
            override fun createAssociatedIdListCache(prop: ImmutableProp): Cache<*, List<*>>? =
                creator.createForProp<Any, List<*>>(prop, filterState.isAffected(prop.targetType))

            // Id -> computed value, for transient properties with resolver
            override fun createResolverCache(prop: ImmutableProp): Cache<*, *>? =
                creator.createForProp<Any, Any>(prop, true)
        }
    }

}
