package io.github.llh4github.ksas.api.auth

import io.github.llh4github.ksas.commons.JsonWrapper
import io.github.llh4github.ksas.commons.PageResult
import io.github.llh4github.ksas.dbmodel.auth.Role
import io.github.llh4github.ksas.dbmodel.auth.dto.RoleAddInput
import io.github.llh4github.ksas.dbmodel.auth.dto.RoleBaseView
import io.github.llh4github.ksas.dbmodel.auth.dto.RoleQuerySpec
import io.github.llh4github.ksas.dbmodel.auth.dto.RoleUpdateInput
import io.github.llh4github.ksas.service.auth.RoleService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
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

    /**
     * 只操作角色数据，关联数据操作使用其他接口
     */
    @PostMapping
    @Operation(summary = "新增角色")
    fun add(@RequestBody @Validated input: RoleAddInput): JsonWrapper<Role> {
        val rs = roleService.addUnique(input)
        return JsonWrapper.ok(rs)
    }

    @PutMapping
    @Operation(summary = "更新角色")
    fun update(
        @RequestBody @Validated input: RoleUpdateInput
    ): JsonWrapper<Role> {
        val rs = roleService.checkAndUpdateById(input)
        return JsonWrapper.ok(rs)
    }

    @PostMapping("page")
    @Operation(summary = "分页查询")
    fun page(
        @RequestBody query: RoleQuerySpec
    ): JsonWrapper<PageResult<RoleBaseView>> {
        val rs = roleService.pageQuery(RoleBaseView::class, query, query.pageParam)
        return JsonWrapper.ok(rs)
    }
}
