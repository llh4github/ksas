package io.github.llh4github.ksas.api.auth

import io.github.llh4github.ksas.commons.JsonWrapper
import io.github.llh4github.ksas.dbmodel.auth.User
import io.github.llh4github.ksas.service.auth.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("auth/user")
class UserApi(private val userService: UserService) {

    @GetMapping
    @Operation(summary = "根据ID获取用户")
    fun getById(id: Long): JsonWrapper<User> {
        val rs = userService.getById(id)
        return JsonWrapper.ok(rs)
    }
}
