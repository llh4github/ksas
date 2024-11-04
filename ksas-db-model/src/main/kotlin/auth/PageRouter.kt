package io.github.llh4github.ksas.dbmodel.auth

import io.github.llh4github.ksas.dbmodel.BaseModel
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.Table

/**
 * 页面路由表
 */
@Entity
@Table(name = "auth_page_router")
interface PageRouter : BaseModel {
    /**
     * 路由路径
     */
    val path: String

    /**
     * 菜单名称
     */
    val title: String

    /**
     * 路由名称（必须保持唯一）
     */
    @Key
    val name: String

    /**
     * 路由重定向（默认跳转地址）
     */
    val redirect: String?

    val icon: String?

    /**
     * 是否在菜单中显示
     */
    val showLink: Boolean

    /**
     * 菜单排序，值越高排的越后（只针对顶级路由）
     */
    val rank: Int

}