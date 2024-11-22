package io.github.llh4github.ksas.api.auth

import io.github.llh4github.ksas.commons.JsonWrapper
import io.github.llh4github.ksas.commons.PageResult
import io.github.llh4github.ksas.dbmodel.auth.PageRouter
import io.github.llh4github.ksas.dbmodel.auth.dto.*
import io.github.llh4github.ksas.service.auth.PageRouterService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
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
    @Operation(summary = "获取所有路由树")
    @GetMapping("/allTree")
    fun allRouterTree(): JsonWrapper<List<PageRouterTreeView>> {
        val rs = pageRouterService.allRouterTree()
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
}
