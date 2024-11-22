package io.github.llh4github.ksas.dbmodel.auth

import io.github.llh4github.ksas.dbmodel.BaseModel
import io.github.llh4github.ksas.dbmodel.enums.MenuType
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

    @get:Schema(title = "菜单类型", description = "0代表菜单、1代表iframe、2代表外链、3代表按钮")
    val menuType: MenuType

    @get:Schema(title = "路由路径", description = "页面路由的路径")
    val path: String

    @Key
    @get:Schema(
        title = "路由名称",
        description = "必须唯一并且和当前路由component字段对应的页面里用defineOptions包起来的name保持一致"
    )
    val name: String

    @get:Schema(
        title = "组件路径",
        description = "传component组件路径，那么path可以随便写，如果不传，component组件路径会跟path保持一致"
    )
    val component: String?

    @get:Schema(title = "路由重定向", description = "页面路由重定向的路径")
    val redirect: String?

    @Formula(dependencies = ["rank", "showLink", "icon", "title", "endpoints"])
    val meta: PageRouterMeta
        get() = PageRouterMeta(rank, showLink, icon, title, endpoints)

    @get:Schema(
        title = "菜单排序",
        description = "平台规定只有home路由的rank才能为0，所以后端在返回rank的时候需要从非0开始"
    )
    val rank: Int

    @Column(name = "show_link")
    @get:Schema(description = "是否在菜单中显示")
    val showLink: Boolean

    @get:Schema(title = "菜单图标", description = "菜单项的图标")
    val icon: String?

    @get:Schema(
        title = "菜单名称",
        description = "兼容国际化、非国际化，如果用国际化的写法就必须在根目录的locales文件夹下对应添加"
    )
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
