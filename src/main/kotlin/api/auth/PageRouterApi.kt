package io.github.llh4github.ksas.api.auth

import io.github.llh4github.ksas.bo.PageRouterTreeBo
import io.github.llh4github.ksas.commons.JsonWrapper
import io.github.llh4github.ksas.commons.PageResult
import io.github.llh4github.ksas.commons.consts.PermCodeConstant
import io.github.llh4github.ksas.dbmodel.auth.PageRouter
import io.github.llh4github.ksas.dbmodel.auth.dto.*
import io.github.llh4github.ksas.library.SecurityUtil
import io.github.llh4github.ksas.service.auth.PageRouterService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * 页面路由API
 */
@Tag(name = "页面路由接口")
@RestController
@RequestMapping("/auth/page/router")
class PageRouterApi(
    private val pageRouterService: PageRouterService
) {
    @Operation(
        summary = "获取当前用户可访问路由树",
        description = "auth:pageRouter:view:tree,${PermCodeConstant.COMMON}"
    )
    @GetMapping("/allTree")
    @PreAuthorize("@pc.hasAnyPermission('auth:pageRouter:view:tree','${PermCodeConstant.COMMON}')")
    fun allRouterTree(): JsonWrapper<List<PageRouterTreeBo>> {
        val rs = pageRouterService.allRouterTree(SecurityUtil.permissions())
        return JsonWrapper.ok(rs)
    }

    @Operation(summary = "分页查询", description = "auth:pageRouter:view:page")
    @PostMapping("/page")
    @PreAuthorize("@pc.hasPermission('auth:pageRouter:view:page')")
    fun pageQuery(
        @RequestBody spec: PageRouterQuerySpec
    ): JsonWrapper<PageResult<PageRouterListView>> {
        val rs = pageRouterService.pageQuery(PageRouterListView::class, spec, spec.pageParam)
        return JsonWrapper.ok(rs)
    }

    @Operation(summary = "新增页面路由", description = "auth:pageRouter:add")
    @PostMapping
    @PreAuthorize("@pc.hasPermission('auth:pageRouter:add')")
    fun add(@RequestBody @Valid input: PageRouterAddInput): JsonWrapper<PageRouter> {
        val rs = pageRouterService.addUnique(input)
        return JsonWrapper.ok(rs)
    }

    @Operation(summary = "更新页面路由", description = "auth:pageRouter:update")
    @PutMapping
    @PreAuthorize("@pc.hasPermission('auth:pageRouter:update')")
    fun update(@RequestBody @Valid input: PageRouterUpdateInput): JsonWrapper<PageRouter> {
        val rs = pageRouterService.updateUnique(input)
        return JsonWrapper.ok(rs)
    }

    @Operation(summary = "级联选择数据接口", description = "auth:pageRouter:view:cascader")
    @GetMapping("/cascader")
    @PreAuthorize("@pc.hasPermission('auth:pageRouter:view:cascader')")
    fun cascader(): JsonWrapper<List<PageRouterCascaderView>> {
        val rs = pageRouterService.cascader()
        return JsonWrapper.ok(rs)
    }

    @Operation(summary = "获取页面路由权限", description = "仅返回权限ID,auth:pageRouter:view:permissions")
    @GetMapping("permissions")
    @PreAuthorize("@pc.hasPermission('auth:pageRouter:view:permissions')")
    fun getPermissionIds(@RequestParam id: Long): JsonWrapper<List<Long>> {
        val rs = pageRouterService.getById(PageRouterPermissionIdView::class, id)
            ?.permissionIds
            .orEmpty()
        return JsonWrapper.ok(rs)
    }

    @Operation(summary = "更新页面路由权限", description = "auth:pageRouter:update:permissions")
    @PutMapping("permissions")
    @PreAuthorize("@pc.hasPermission('auth:pageRouter:update:permissions')")
    fun updatePermissionIds(
        @RequestBody @Validated input: PageRouterPermissionUpdateInput
    ): JsonWrapper<Boolean> {
        val rs = pageRouterService.updatePermission(input)
        return JsonWrapper.ok(rs)
    }
}
