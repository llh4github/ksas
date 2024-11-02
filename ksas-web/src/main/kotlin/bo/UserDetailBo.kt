package io.github.llh4github.ksas.bo

import io.github.llh4github.ksas.dbmodel.auth.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 * 用户详情业务对象。用于Spring Security的用户认证
 */
class UserDetailBo(private val user: User) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String {
        return user.username
    }
}
