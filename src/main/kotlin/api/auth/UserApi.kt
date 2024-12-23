package io.github.llh4github.ksas.api.auth

import io.github.llh4github.ksas.commons.JsonWrapper
import io.github.llh4github.ksas.commons.PageResult
import io.github.llh4github.ksas.dbmodel.auth.User
import io.github.llh4github.ksas.dbmodel.auth.dto.*
import io.github.llh4github.ksas.service.auth.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Tag(name = "用户管理接口")
@RestController
@RequestMapping("auth/user")
class UserApi(private val userService: UserService) {

    @GetMapping
    @Operation(summary = "根据ID获取用户")
    fun getById(id: Long): JsonWrapper<User> {
        val rs = userService.getById(id)
        return JsonWrapper.ok(rs)
    }

    @PostMapping
    @Operation(summary = "新增用户")
    fun add(@RequestBody @Validated input: UserAddInput): JsonWrapper<User> {
        val rs = userService.addUnique(input)
        return JsonWrapper.ok(rs)
    }

    @PutMapping("update/roles")
    @Operation(summary = "修改用户拥有角色关系")
    fun updateRole(
        @RequestBody @Validated input: UserUpdateRoleInput
    ): JsonWrapper<Boolean> {
        val rs = userService.updateRole(input)
        return JsonWrapper.ok(rs)
    }

    @PostMapping("page")
    @Operation(summary = "分页查询")
    fun page(
        @RequestBody query: UserQuerySpec
    ): JsonWrapper<PageResult<UserBaseView>> {
        val rs = userService.pageQuery(UserBaseView::class, query, query.pageParam)
        return JsonWrapper.ok(rs)
    }

    @PutMapping("update/password")
    @Operation(summary = "更新密码")
    fun resetPwd(
        @RequestBody @Validated input: UserRestPwdInput
    ): JsonWrapper<Boolean> {
        val rs = userService.updatePwd(input)
        return JsonWrapper.ok(rs)
    }
}
