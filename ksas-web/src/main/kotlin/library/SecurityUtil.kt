package io.github.llh4github.ksas.library

import io.github.llh4github.ksas.bo.AccountAuthBo
import org.springframework.security.core.context.SecurityContextHolder

/**
 * SpringSecurity工具类
 */
object SecurityUtil {

    @Suppress("MaxLineLength")
    private fun authBo(): AccountAuthBo {
        return (SecurityContextHolder.getContext().authentication) as AccountAuthBo
    }

    /**
     * 获取当前用户ID
     */
    fun userId(): Long {
        return authBo().getUserId()
    }

    /**
     * 获取当前用户名
     */
    fun username(): String {
        return authBo().principal as String
    }
}
