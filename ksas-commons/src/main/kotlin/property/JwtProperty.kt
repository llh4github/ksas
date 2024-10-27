package io.github.llh4github.ksas.commons.property

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import kotlin.time.Duration
import kotlin.time.toJavaDuration


class JwtProperty {
    /**
     * 签发人。通常是访问域名。
     */
    var issuer: String = "ksas-web"

    var tokenHeaderName = "Authorization"

    var tokenHeaderPrefix = "Bearer "

    /**
     * 令牌秘钥
     *
     * 至少需要43个字符，不含特殊符号。
     */
    var secret: String = "VyHZ8YGV9w94dRw8ixVzJgcoDXqvRGrei2339zCxiMIgbgmM"

    /**
     * 令牌过期时间
     */
    var tokenExpireTime: TokenExpireTime = TokenExpireTime()

    /**
     * 缓存key前缀
     */
    var cacheKeyPrefix: String = "ksas:jwt:"

}

data class TokenExpireTime(
    var access: Duration = Duration.parse("1d"),
    var refresh: Duration = Duration.parse("7d"),
) {
    val accessExpireTime: Date
        get() {
            val instant = LocalDateTime.now()
                .plus(access.toJavaDuration())
                .atZone(ZoneId.systemDefault()).toInstant()
            return Date.from(instant)
        }

    val refreshExpireTime: Date
        get() {
            val instant = LocalDateTime.now()
                .plus(refresh.toJavaDuration())
                .atZone(ZoneId.systemDefault()).toInstant()
            return Date.from(instant)
        }
}

enum class JwtType {
    ACCESS, REFRESH
}
