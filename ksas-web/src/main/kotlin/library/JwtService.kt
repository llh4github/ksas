package io.github.llh4github.ksas.library

import io.github.llh4github.ksas.commons.LongIdGenerator
import io.github.llh4github.ksas.commons.property.JwtType
import io.github.llh4github.ksas.commons.property.WebSecurityProperty
import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.impl.DefaultClaims
import io.jsonwebtoken.impl.lang.Parameter
import io.jsonwebtoken.impl.lang.Parameters
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.TimeUnit
import javax.crypto.SecretKey

@Component
class JwtService(
    private val webProperty: WebSecurityProperty,
    private val idGenerator: LongIdGenerator,
    private val redisTemplate: StringRedisTemplate,
) {
    private val logger = KotlinLogging.logger {}
    private val jwtProperty by lazy { webProperty.jwt }

    private val userIdKey = "userid"
    private val userIdParameter: Parameter<String> =
        Parameters.string("userid", "UserID")

    object JwtKeys {
        const val ROLES_KEY = "role"
    }

    /**
     * 生成密钥
     */
    private val secretKey: SecretKey by lazy {
        val bytes = Decoders.BASE64.decode(jwtProperty.secret)
        Keys.hmacShaKeyFor(bytes)
    }

    private val parser by lazy {
        Jwts.parser().verifyWith(secretKey).build()
    }

    /**
     * 创建并缓存jwt
     *
     * @param subject jwt签发对象，为用户名
     * @param type jwt类型
     * @param block jwt内容,不宜过多
     */
    fun createToken(
        subject: String,
        userId: Long,
        type: JwtType = JwtType.ACCESS,
        block: (JwtKeys) -> Map<String, Any>
    ): String {
        val expire = if (type == JwtType.ACCESS) {
            jwtProperty.tokenExpireTime.accessExpireTime
        } else {
            jwtProperty.tokenExpireTime.refreshExpireTime
        }

        val idStr = idGenerator.nextIdStr()
        val builder = Jwts.builder()
            .id(idStr)
            .subject(subject)
            .claims(block(JwtKeys))
            .issuer(jwtProperty.issuer)
            .issuedAt(Date())
            .signWith(secretKey)
            .expiration(expire)
        builder.claim(userIdKey, userId.toString())
        builder.header().add("typ", type.name)
        val jwt = builder.compact()

        val key = "${webProperty.cacheJwtPrefix}:$subject:$idStr"
        redisTemplate.opsForValue().set(
            key,
            jwt,
            expire.time - System.currentTimeMillis(),
            TimeUnit.MILLISECONDS
        )
        return jwt
    }


    /**
     * 从JWT中获取用户id
     *
     * 此方法应当在验证token有效性之后调用，否则可能抛出异常
     * @return jwt中的subject字段
     */
    fun getUserId(jwt: String): Long {
        val claims = parser.parse(jwt).payload as DefaultClaims
        return claims.get(userIdParameter).toLong()
    }

    /**
     * 验证token是否有效
     *
     * @return 验证失败返回false
     */
    @Suppress("TooGenericExceptionCaught")
    fun isValid(jwt: String): Boolean {
        return try {
            val claims = parser.parse(jwt).payload as DefaultClaims
            val jwtId = claims.id
            val subject = claims.subject
            val key = "${webProperty.cacheJwtPrefix}:$subject:$jwtId"
            val cacheJwt: String? = redisTemplate.opsForValue().get(key)
            if (cacheJwt == null || cacheJwt != jwt) {
                logger.debug {
                    "token验证失败，与缓存中的JWT对不上。缓存key为: $key ,输入的jwt为: $jwt"
                }
                return false
            }
            true
        } catch (e: RuntimeException) {
            logger.warn(e) { "token验证出错: $jwt" }
            false
        }
    }
}
