package io.github.llh4github.ksas.library

import io.github.llh4github.ksas.bo.AccountAuthBo
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
import javax.crypto.SecretKey
import kotlin.time.toJavaDuration

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
        const val ROLES_KEY = "roles"
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


    fun createToken(
        subject: String,
        userId: Long,
        type: JwtType = JwtType.ACCESS,
        block: (JwtKeys) -> Map<String, Any> = { emptyMap() }
    ): String {
        return createExpireToken(subject, userId, type, block).first
    }

    /**
     * 创建并缓存jwt
     *
     * @param subject jwt签发对象，为用户名
     * @param type jwt类型
     * @param block jwt内容,不宜过多
     * @return jwt和过期时间
     */
    fun createExpireToken(
        subject: String,
        userId: Long,
        type: JwtType = JwtType.ACCESS,
        block: (JwtKeys) -> Map<String, Any> = { emptyMap() }
    ): Pair<String, Date> {
        val expireTime = if (type == JwtType.ACCESS) {
            jwtProperty.tokenExpireTime.accessExpireTime
        } else {
            jwtProperty.tokenExpireTime.refreshExpireTime
        }
        val expiration = if (type == JwtType.ACCESS) {
            jwtProperty.tokenExpireTime.access
        } else {
            jwtProperty.tokenExpireTime.refresh
        }

        val idStr = idGenerator.nextIdStr()
        val builder = Jwts.builder()
            .id(idStr)
            .subject(subject)
            .issuer(jwtProperty.issuer)
            .issuedAt(Date())
            .signWith(secretKey)
            .expiration(expireTime)
        block(JwtKeys).takeIf { it.isNotEmpty() }.let {
            builder.claims(it)
        }
        builder.claim(userIdKey, userId.toString())
        builder.header().add("typ", type.name)
        val jwt = builder.compact()

        val key = "${webProperty.cacheJwtPrefix}:$subject:$idStr"
        redisTemplate.opsForValue().set(key, jwt, expiration.toJavaDuration())
        return Pair(jwt, expireTime)
    }

    fun banJwt(jwt: String) {
        // 如果jwt无效，则不需要禁用
        val claims = validAndClaims(jwt) ?: return
        val jwtId = claims.id
        val subject = claims.subject
        val key = "${webProperty.cacheJwtPrefix}:$subject:$jwtId"
        redisTemplate.delete(key)
    }

    /**
     * 从JWT中获取用户id
     * @return jwt中的subject字段
     */
    fun validAndGetUserId(jwt: String): Long? {
        return validAndClaims(jwt)?.get(userIdParameter)?.toLong()
    }


    /**
     * 验证并获取用户名
     * @return jwt中的subject字段
     */
    fun validAndGetUsername(jwt: String): String? {
        return validAndClaims(jwt)?.subject
    }

    fun validAndAuthBo(jwt: String): AccountAuthBo? {
        val claims = validAndClaims(jwt) ?: return null
        val bo = AccountAuthBo(
            claims.get(userIdParameter).toLong(),
            claims.subject
        )
        bo.isAuthenticated = true
        return bo
    }

    /**
     * 验证token是否有效
     *
     * @return 验证失败返回false
     */
    fun isValid(jwt: String): Boolean {
        return validAndClaims(jwt) != null
    }

    /**
     * 验证token是否有效，并返回claims
     *
     * @return 验证失败返回null
     */
    @Suppress("TooGenericExceptionCaught")
    private fun validAndClaims(jwt: String): DefaultClaims? {
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
                return null
            }
            claims
        } catch (e: RuntimeException) {
            logger.warn(e) { "token验证出错: $jwt" }
            null
        }
    }

}
