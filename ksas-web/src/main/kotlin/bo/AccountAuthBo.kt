package io.github.llh4github.ksas.bo

import org.springframework.security.authentication.AbstractAuthenticationToken

/**
 * 账户认证业务对象
 */
class AccountAuthBo(
    private val userId: Long,
    private val username: String,
) : AbstractAuthenticationToken(emptyList()) {

    fun getUserId(): Long {
        return userId
    }

    /**
     * 获取凭证。这里返回空字符串，因为密码不应该被存储在内存中
     */
    override fun getCredentials(): Any {
        return ""
    }

    override fun getPrincipal(): Any {
        return username
    }
}
