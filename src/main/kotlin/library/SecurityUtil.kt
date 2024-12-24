package io.github.llh4github.ksas.library

import io.github.llh4github.ksas.bo.AccountAuthBo
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder

/**
 * SpringSecurity工具类
 */
object SecurityUtil {

    @Suppress("MaxLineLength")
    private fun authBo(): AccountAuthBo {
        return try {
            (SecurityContextHolder.getContext().authentication) as AccountAuthBo
        } catch (e: Exception) {
            throw AccessDeniedException("无法获取当前用户信息")
        }
    }

    /**
     * 获取当前用户ID
     */
    fun userId(): Long {
        return authBo().getUserId()
    }

    /**
     * 清除登录信息
     */
    fun clearLoginInfo() {
        SecurityContextHolder.clearContext()
    }

    /**
     * 获取当前用户名
     */
    fun username(): String {
        return authBo().principal as String
    }

    fun authorities(): Collection<GrantedAuthority> {
        return authBo().authorities
    }
    fun permissions(): List<String> {
        return authBo().authorities.map { it.authority }
    }
}
