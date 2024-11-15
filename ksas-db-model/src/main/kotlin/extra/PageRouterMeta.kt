package io.github.llh4github.ksas.dbmodel.extra

import com.fasterxml.jackson.annotation.JsonIgnore
import io.github.llh4github.ksas.dbmodel.auth.EndpointPerm
import io.swagger.v3.oas.annotations.media.Schema

data class PageRouterMeta(

    @get:Schema(description = "菜单排序，值越高排的越后（只针对顶级路由）")
    val rank: Int,

    @get:Schema(description = "是否在菜单中显示")
    val showLink: Boolean,

    @get:Schema(description = "路由元信息")
    val icon: String?,

    @get:Schema(description = "菜单名称")
    val title: String,

    @get:JsonIgnore
    val endpoints: List<EndpointPerm> = emptyList(),
) {
    @get:Schema(description = "权限码集合")
    val auths = endpoints.map { it.permCode }
}
