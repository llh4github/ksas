package io.github.llh4github.ksas.api.auth

import io.github.llh4github.ksas.commons.JsonWrapper
import io.github.llh4github.ksas.dbmodel.auth.Role
import io.github.llh4github.ksas.dbmodel.auth.dto.RoleAddInput
import io.github.llh4github.ksas.service.auth.RoleService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("auth/role")
@Tag(name = "角色管理接口")
class RoleApi(private val roleService: RoleService) {

    @Operation(summary = "根据ID获取角色")
    @GetMapping
    fun getById(id: Long): JsonWrapper<Role> {
        val rs = roleService.getById(id)
        return JsonWrapper.ok(rs)
    }

    @PostMapping
    @Operation(summary = "新增角色")
    fun add(@RequestBody input: RoleAddInput): JsonWrapper<Role> {
        val rs = roleService.addUnique(input)
        return JsonWrapper.ok(rs)
    }
}
