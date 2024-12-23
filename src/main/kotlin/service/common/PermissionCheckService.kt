package io.github.llh4github.ksas.service.common

import io.github.llh4github.ksas.library.SecurityUtil
import org.springframework.stereotype.Service
import org.springframework.util.AntPathMatcher

/**
 * 权限检查服务
 * 基本用法：
 * `@PreAuthorize("@pc.hasPermission('auth:role:view:list')")`
 */
@Service(value = "pc")
class PermissionCheckService {
    private val matcher = AntPathMatcher(":")

    fun hasPermission(permission: String): Boolean {
        return SecurityUtil.authorities().any { matcher.match(it.authority, permission) }
    }

    fun hasAnyPermission(vararg permissions: String): Boolean {
        return permissions.any { hasPermission(it) }
    }

    /**
     * 检查是否有任意一个权限
     * @param patterns 被检查的权限
     * @param source 输入权限
     */
    fun checkHasPermission(patterns: List<String>, source: List<String>): Boolean {
        return patterns.any { ele ->
            source.any { matcher.match(ele, it) }
        }
    }
}
