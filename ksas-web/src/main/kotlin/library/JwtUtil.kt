package io.github.llh4github.ksas.library

import io.github.llh4github.ksas.commons.IdGeneratorTrait
import io.github.llh4github.ksas.commons.property.JwtProperty
import io.github.llh4github.ksas.commons.property.JwtType
import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.impl.DefaultClaims
import io.jsonwebtoken.impl.lang.Parameter
import io.jsonwebtoken.impl.lang.Parameters
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import javax.crypto.SecretKey

@Component
class JwtUtil(
    private val property: JwtProperty,
    private val idGenerator: IdGeneratorTrait
) {
    private val logger = KotlinLogging.logger {}

    object JwtKeys {
        const val USER_ID_KEY = "userid"
        val USER_ID: Parameter<String> = Parameters.string(USER_ID_KEY, "USER_ID")
    }

    /**
     * 生成密钥
     */
    private val secretKey: SecretKey by lazy {
        val bytes = Decoders.BASE64.decode(property.secret)
        Keys.hmacShaKeyFor(bytes)
    }

    private val parser by lazy {
        Jwts.parser().verifyWith(secretKey).build()
    }

    fun createToken(type: JwtType = JwtType.ACCESS, block: (JwtKeys) -> Map<String, Any>): String {
        val expire = if (type == JwtType.ACCESS) {
            property.tokenExpireTime.accessExpireTime
        } else {
            property.tokenExpireTime.refreshExpireTime
        }

        val builder = Jwts.builder()
            .id(idGenerator.nextIdStr())
            .claims(block(JwtKeys))
            .issuer(property.issuer)
            .signWith(secretKey)
            .expiration(expire)
        builder.header().add("typ", type.name)
        return builder.compact()
    }

    /**
     * 解析token。
     *
     * @return 解析失败返回null
     */
    @Suppress("TooGenericExceptionCaught")
    fun parse(jwt: String): DefaultClaims? {
        return try {
            parser.parse(jwt).payload as DefaultClaims
        } catch (e: RuntimeException) {
            logger.warn(e) { "token验证出错: $jwt" }
            null
        }
    }

    /**
     * 验证token是否有效
     *
     * @return 验证失败返回false
     */
    @Suppress("TooGenericExceptionCaught")
    fun isValid(jwt: String): Boolean {
        return try {
            parser.parse(jwt)
            true
        } catch (e: RuntimeException) {
            logger.warn(e) { "token验证出错: $jwt" }
            false
        }
    }

}
