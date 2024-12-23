package io.github.llh4github.ksas.api.auth

import io.github.llh4github.ksas.commons.JsonWrapper
import io.github.llh4github.ksas.commons.PageResult
import io.github.llh4github.ksas.dbmodel.auth.Permission
import io.github.llh4github.ksas.dbmodel.auth.dto.*
import io.github.llh4github.ksas.service.auth.PermissionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Tag(name = "权限接口")
@RequestMapping("/auth/permission")
@RestController
class PermissionApi(private val permissionService: PermissionService) {

    @Operation(summary = "分页查询", description = "auth:permission:view:page")
    @PostMapping("/page")
    @PreAuthorize("@pc.hasPermission('auth:permission:view:page')")
    fun pageQuery(
        @RequestBody spec: PermissionQuerySpec
    ): JsonWrapper<PageResult<PermissionBaseView>> {
        val rs = permissionService.pageQuery(PermissionBaseView::class, spec, spec.pageParam)
        return JsonWrapper.ok(rs)
    }

    @Operation(summary = "新增权限", description = "auth:permission:add")
    @PreAuthorize("@pc.hasPermission('auth:permission:add')")
    @PostMapping
    fun add(
        @RequestBody @Validated input: PermissionAddInput
    ): JsonWrapper<Permission> {
        val rs = permissionService.addUnique(input)
        return JsonWrapper.ok(rs)
    }

    @GetMapping("/simpleData")
    @PreAuthorize("@pc.hasPermission('auth:permission:view:page')")
    @Operation(
        summary = "查询简单数据", description = "查询所有数据，慎用。本接口返回少量字段。\n" +
                "auth:permission:view:page"
    )
    fun simple(): JsonWrapper<List<PermissionSimpleView>> {
        val rs = permissionService.listQuery(PermissionSimpleView::class)
        return JsonWrapper.ok(rs)
    }

    @Operation(summary = "更新权限", description = "auth:permission:update")
    @PutMapping
    @PreAuthorize("@pc.hasPermission('auth:permission:update')")
    fun update(
        @RequestBody @Validated input: PermissionUpdateInput
    ): JsonWrapper<Permission> {
        val rs = permissionService.updateUnique(input)
        return JsonWrapper.ok(rs)
    }

    @Operation(summary = "级联(树形)数据查询", description = "auth:permission:view:cascader")
    @GetMapping("cascader")
    @PreAuthorize("@pc.hasPermission('auth:permission:view:cascader')")
    @Parameters(
        Parameter(name = "id", description = "节点ID", required = false, example = "114514")
    )
    fun cascader(
        @RequestParam("id") id: Long? = null
    ): JsonWrapper<PermissionCasecaderView> {
        val root = permissionService.treeData(id)
        return JsonWrapper.ok(root)
    }
}
