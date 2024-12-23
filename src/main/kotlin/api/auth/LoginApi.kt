package io.github.llh4github.ksas.api.auth

import io.github.llh4github.ksas.bo.LoginResultBo
import io.github.llh4github.ksas.bo.LogoutParam
import io.github.llh4github.ksas.bo.RefreshJwtBo
import io.github.llh4github.ksas.commons.JsonWrapper
import io.github.llh4github.ksas.dbmodel.auth.dto.UserLoginView
import io.github.llh4github.ksas.service.auth.LoginService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@Tag(name = "登录登出接口")
@RestController
class LoginApi(private val loginService: LoginService) {


    @Operation(summary = "登录接口")
    @PostMapping("login")
    fun login(
        @RequestBody @Validated view: UserLoginView
    ): JsonWrapper<LoginResultBo> {
        val rs = loginService.login(view)
        return JsonWrapper.ok(rs)
    }

    @Operation(summary = "刷新凭证接口")
    @PostMapping("token/refresh")
    fun refreshToken(
        @RequestBody @Validated param: RefreshJwtBo
    ): JsonWrapper<LoginResultBo> {
        val rs = loginService.refreshToken(param)
        return JsonWrapper.ok(rs)
    }

    @Operation(summary = "登出接口")
    @PostMapping("logout")
    fun logout(
        @RequestBody @Validated param: LogoutParam
    ): JsonWrapper<Boolean> {
        val rs = loginService.logout(param)
        return JsonWrapper.ok(rs)
    }
}
