package io.github.llh4github.ksas.api.auth

import io.github.llh4github.ksas.commons.JsonWrapper
import io.github.llh4github.ksas.commons.PageResult
import io.github.llh4github.ksas.dbmodel.auth.EndpointPerm
import io.github.llh4github.ksas.dbmodel.auth.dto.*
import io.github.llh4github.ksas.service.auth.EndpointPermService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@Tag(name = "端点权限接口")
@RequestMapping("/auth/endpoint/perm")
@RestController
class EndpointPermApi(
    private val endpointPermService: EndpointPermService
) {

    @PostMapping
    @Operation(summary = "新增端点权限")
    fun add(@RequestBody @Valid input: EndpointPermAddInput): JsonWrapper<EndpointPerm> {
        val rs = endpointPermService.addUnique(input)
        return JsonWrapper.ok(rs)
    }

    @PutMapping
    @Operation(summary = "更新端点权限")
    fun update(@RequestBody @Valid input: EndpointPermUpdateInput): JsonWrapper<EndpointPerm> {
        val rs = endpointPermService.updateUnique(input)
        return JsonWrapper.ok(rs)
    }

    @Operation(summary = "分页查询")
    @PostMapping("/page")
    fun pageQuery(
        @RequestBody spec: EndpointPermQuerySpec
    ): JsonWrapper<PageResult<EndpointPermBaseView>> {
        val rs = endpointPermService.pageQuery(EndpointPermBaseView::class, spec, spec.pageParam)
        return JsonWrapper.ok(rs)
    }

    @GetMapping("/simpleData")
    @Operation(summary = "查询简单数据", description = "查询所有数据，慎用。本接口返回少量字段。")
    fun simple(): JsonWrapper<List<EndpointPermSimpleView>> {
        val rs = endpointPermService.listQuery(EndpointPermSimpleView::class)
        return JsonWrapper.ok(rs)
    }
}
