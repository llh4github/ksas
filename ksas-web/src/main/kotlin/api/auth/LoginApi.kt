package io.github.llh4github.ksas.api.auth

import io.github.llh4github.ksas.dbmodel.auth.dto.UserLoginView
import io.github.llh4github.ksas.service.auth.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController


@Tag(name = "登录登出接口")
@RestController
class LoginApi(private val userService: UserService) {


    @Operation(summary = "登录接口")
    @PostMapping("login")
    fun login(view : UserLoginView) {
        // TODO
    }
}