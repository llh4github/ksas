package io.github.llh4github.ksas.commons.util

import io.github.llh4github.ksas.commons.consts.PermCodeConstant

/**
 * 权限码工具类
 */
object PermCodeUtil {
    fun isSuperPerm(input: String): Boolean {
        return PermCodeConstant.SUPER_PERM == input
    }
    fun hasAny(input: String, target: String): Boolean {
        return false
    }
}
