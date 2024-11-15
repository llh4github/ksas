package io.github.llh4github.ksas.dbmodel.auth

import io.github.llh4github.ksas.dbmodel.BaseModel
import io.github.llh4github.ksas.dbmodel.extra.PageRouterMeta
import io.swagger.v3.oas.annotations.media.Schema
import org.babyfish.jimmer.Formula
import org.babyfish.jimmer.sql.*

/**
 * 页面路由表
 */
@Entity
@Table(name = "auth_page_router")
interface PageRouter : BaseModel {

    @get:Schema(description = "路由路径")
    val path: String

    @Key
    @get:Schema(description = "路由名称")
    val name: String

    @get:Schema(description = "路由重定向")
    val redirect: String?

    @Formula(dependencies = ["rank", "showLink", "icon", "title", "endpoints"])
    val meta: PageRouterMeta
        get() = PageRouterMeta(rank, showLink, icon, title, endpoints)

    @get:Schema(description = "菜单排序，值越高排的越后（只针对顶级路由）")
    val rank: Int

    /**
     * 是否在菜单中显示
     */
    @Column(name = "show_link")
    @get:Schema(description = "是否在菜单中显示")
    val showLink: Boolean

    @get:Schema(description = "路由元信息")
    val icon: String?

    /**
     * 菜单名称
     */
    @get:Schema(description = "菜单名称")
    val title: String

    @ManyToMany
    @JoinTable(
        name = "link_router_endpoint",
        joinColumnName = "router_id",
        inverseJoinColumnName = "endpoint_id"
    )
    @get:Schema(description = "页面路由关联的接口权限")
    val endpoints: List<EndpointPerm>

    @ManyToOne
    val parent: PageRouter?

    @OneToMany(mappedBy = "parent")
    val children: List<PageRouter>
}
