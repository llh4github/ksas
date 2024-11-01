package io.github.llh4github.ksas.service.common

import io.github.llh4github.ksas.library.JwtUtil
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service

@Service
class JwtService(
    private val redisTemplate: StringRedisTemplate,
    private val jwtUtil: JwtUtil
) {
    private val logger = KotlinLogging.logger {}

    fun verifyJwt(jwt: String): Boolean {
        val payload = jwtUtil.isValid(jwt)
        return payload != null
    }

}