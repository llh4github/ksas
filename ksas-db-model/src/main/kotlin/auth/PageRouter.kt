package io.github.llh4github.ksas.dbmodel.auth

import io.github.llh4github.ksas.dbmodel.BaseModel
import io.swagger.v3.oas.annotations.media.Schema
import org.babyfish.jimmer.sql.*

/**
 * 页面路由表
 */
@Entity
@Table(name = "auth_page_router")
interface PageRouter : BaseModel {
    /**
     * 路由路径
     */
    @get:Schema(description = "路由路径")
    val path: String

    /**
     * 路由名称（必须保持唯一）
     */
    @Key
    @get:Schema(description = "路由名称")
    val name: String

    /**
     * 路由重定向（默认跳转地址）
     */
    @get:Schema(description = "路由重定向")
    val redirect: String?

    /**
     * 菜单排序，值越高排的越后（只针对顶级路由）
     */
    @get:Schema(description = "菜单排序，值越高排的越后（只针对顶级路由）")
    val rank: Int

    /**
     * 是否在菜单中显示
     */
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
    val endpoints: List<EndpointPerm>

    @ManyToOne
    val parent: PageRouter?

    @OneToMany(mappedBy = "parent")
    val children: List<PageRouter>
}