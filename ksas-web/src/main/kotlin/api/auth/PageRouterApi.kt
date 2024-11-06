package io.github.llh4github.ksas.api.auth

import io.github.llh4github.ksas.commons.JsonWrapper
import io.github.llh4github.ksas.dbmodel.auth.dto.PageRouterTreeView
import io.github.llh4github.ksas.service.auth.PageRouterService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}
