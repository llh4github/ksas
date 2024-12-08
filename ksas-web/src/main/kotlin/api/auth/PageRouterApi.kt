package io.github.llh4github.ksas.api.auth

import io.github.llh4github.ksas.commons.JsonWrapper
import io.github.llh4github.ksas.commons.PageResult
import io.github.llh4github.ksas.dbmodel.auth.PageRouter
import io.github.llh4github.ksas.dbmodel.auth.dto.*
import io.github.llh4github.ksas.library.SecurityUtil
import io.github.llh4github.ksas.service.auth.PageRouterService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
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
    @Operation(summary = "获取当前用户可访问路由树")
    @GetMapping("/allTree")
    fun allRouterTree(): JsonWrapper<List<PageRouterTreeView>> {
        val rs = pageRouterService.allRouterTree(SecurityUtil.username())
        return JsonWrapper.ok(rs)
    }

    @Operation(summary = "分页查询")
    @PostMapping("/page")
    fun pageQuery(
        @RequestBody spec: PageRouterQuerySpec
    ): JsonWrapper<PageResult<PageRouterListView>> {
        val rs = pageRouterService.pageQuery(PageRouterListView::class, spec, spec.pageParam)
        return JsonWrapper.ok(rs)
    }

    @Operation(summary = "新增页面路由")
    @PostMapping
    fun add(@RequestBody @Valid input: PageRouterAddInput): JsonWrapper<PageRouter> {
        val rs = pageRouterService.addUnique(input)
        return JsonWrapper.ok(rs)
    }

    @Operation(summary = "更新页面路由")
    @PutMapping
    fun update(@RequestBody @Valid input: PageRouterUpdateInput): JsonWrapper<PageRouter> {
        val rs = pageRouterService.updateUnique(input)
        return JsonWrapper.ok(rs)
    }

    @Operation(summary = "级联选择数据接口")
    @GetMapping("/cascader")
    fun cascader(): JsonWrapper<List<PageRouterCascaderView>> {
        val rs = pageRouterService.cascader()
        return JsonWrapper.ok(rs)
    }

    @Operation(summary = "获取页面路由权限", description = "仅返回权限ID")
    @GetMapping("permissions")
    fun getPermissionIds(@RequestParam id: Long): JsonWrapper<List<Long>> {
        val rs = pageRouterService.getById(PageRouterPermissionIdView::class, id)
            ?.endpointIds
            .orEmpty()
        return JsonWrapper.ok(rs)
    }

    @Operation(summary = "更新页面路由权限")
    @PutMapping("permissions")
    fun updatePermissionIds(
        @RequestBody @Validated input: PageRouterPermissionUpdateInput
    ): JsonWrapper<Boolean> {
        val rs = pageRouterService.updatePermission(input)
        return JsonWrapper.ok(rs)
    }
}
