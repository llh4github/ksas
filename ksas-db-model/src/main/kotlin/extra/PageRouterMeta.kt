package io.github.llh4github.ksas.dbmodel.extra

import io.swagger.v3.oas.annotations.media.Schema
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Embeddable

@Embeddable
interface PageRouterMeta {

    /**
     * 菜单排序，值越高排的越后（只针对顶级路由）
     */
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
}
