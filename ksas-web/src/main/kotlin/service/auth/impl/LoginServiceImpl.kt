package io.github.llh4github.ksas.service.auth.impl

import io.github.llh4github.ksas.bo.LoginResultBo
import io.github.llh4github.ksas.bo.LogoutParam
import io.github.llh4github.ksas.commons.property.JwtType
import io.github.llh4github.ksas.dbmodel.auth.User
import io.github.llh4github.ksas.dbmodel.auth.dto.UserLoginView
import io.github.llh4github.ksas.dbmodel.auth.username
import io.github.llh4github.ksas.exception.UserModuleException
import io.github.llh4github.ksas.library.JwtService
import io.github.llh4github.ksas.library.SecurityUtil
import io.github.llh4github.ksas.service.BaseServiceImpl
import io.github.llh4github.ksas.service.auth.LoginService
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class LoginServiceImpl(
    private val sqlClient: KSqlClient,
    private val jwtService: JwtService,
) : BaseServiceImpl<User>(User::class, sqlClient), LoginService {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    override fun login(view: UserLoginView): LoginResultBo {
        val user = createQuery {
            where(table.username eq view.username)
            select(table)
        }.fetchOneOrNull() ?: throw UserModuleException.usernameNoExists(
            message = LOGIN_FAIL_MSG
        )
        if (!passwordEncoder.matches(view.password, user.password)) {
            throw UserModuleException.passwordError(
                message = LOGIN_FAIL_MSG
            )
        }
        val access = jwtService.createToken(user.username, user.id, JwtType.ACCESS)
        val refresh = jwtService.createToken(user.username, user.id, JwtType.REFRESH)

        return LoginResultBo(
            userId = user.id,
            username = user.username,
            accessToken = access,
            refreshToken = refresh,
        )
    }

    override fun logout(param: LogoutParam): Boolean {
        SecurityUtil.clearLoginInfo()
        jwtService.banJwt(param.accessToken)
        jwtService.banJwt(param.refreshToken)
        return true
    }
}

private const val LOGIN_FAIL_MSG = "用户名不存在或密码不正确"