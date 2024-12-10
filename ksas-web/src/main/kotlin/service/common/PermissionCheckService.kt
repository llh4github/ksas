package io.github.llh4github.ksas.service.common

import io.github.llh4github.ksas.library.SecurityUtil
import org.springframework.stereotype.Service
import org.springframework.util.AntPathMatcher

@Service(value = "pc")
class PermissionCheckService {
    private val matcher = AntPathMatcher(":")

    fun hasPermission(
        permission: String
    ): Boolean {
        return SecurityUtil.authorities().any { matcher.match(it.authority, permission) }
    }
}
