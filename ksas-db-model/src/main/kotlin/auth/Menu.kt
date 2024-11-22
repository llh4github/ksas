package io.github.llh4github.ksas.dbmodel.auth

import io.github.llh4github.ksas.dbmodel.enums.MenuType
import io.swagger.v3.oas.annotations.media.Schema

/**
 * 前端页面定义的字段，调试完后删除此文件
 */
interface Menu {

    @get:Schema(title = "菜单类型", description = "0代表菜单、1代表iframe、2代表外链、3代表按钮")
    val menuType: MenuType

    @get:Schema(title = "父菜单ID", description = "父菜单的唯一标识")
    val parentId: Any

    @get:Schema(
        title = "菜单名称",
        description = "兼容国际化、非国际化，如果用国际化的写法就必须在根目录的locales文件夹下对应添加"
    )
    val title: String

    @get:Schema(
        title = "路由名称",
        description = "必须唯一并且和当前路由component字段对应的页面里用defineOptions包起来的name保持一致"
    )
    val name: String

    @get:Schema(title = "路由路径", description = "页面路由的路径")
    val path: String

    @get:Schema(
        title = "组件路径",
        description = "传component组件路径，那么path可以随便写，如果不传，component组件路径会跟path保持一致"
    )
    val component: String?

    @get:Schema(
        title = "菜单排序",
        description = "平台规定只有home路由的rank才能为0，所以后端在返回rank的时候需要从非0开始"
    )
    val rank: Int

    @get:Schema(title = "路由重定向", description = "页面路由重定向的路径")
    val redirect: String?

    @get:Schema(title = "菜单图标", description = "菜单项的图标")
    val icon: String?

    @get:Schema(title = "右侧图标", description = "菜单项右侧的图标")
    val extraIcon: String?

    @get:Schema(title = "进场动画", description = "页面加载时的进场动画")
    val enterTransition: String?

    @get:Schema(title = "离场动画", description = "页面卸载时的离场动画")
    val leaveTransition: String?

    @get:Schema(
        title = "菜单激活",
        description = "将某个菜单激活，主要用于通过query或params传参的路由，" +
                "当它们通过配置showLink: false后不在菜单中显示，就不会有任何菜单高亮，" +
                "而通过设置activePath指定激活菜单即可获得高亮，activePath为指定激活菜单的path"
    )
    val activePath: String?

    @get:Schema(title = "权限标识", description = "按钮级别权限设置")
    val auths: List<String>?

    @get:Schema(title = "链接地址", description = "需要内嵌的iframe链接地址")
    val frameSrc: String?

    @get:Schema(title = "加载动画", description = "内嵌的iframe页面是否开启首次加载动画")
    val frameLoading: Boolean?

    @get:Schema(title = "缓存页面", description = "是否缓存该路由页面，开启后会保存该页面的整体状态，刷新后会清空状态")
    val keepAlive: Boolean

    @get:Schema(title = "标签页", description = "当前菜单名称或自定义信息禁止添加到标签页")
    val hiddenTag: Boolean

    @get:Schema(title = "固定标签页", description = "当前菜单名称是否固定显示在标签页且不可关闭")
    val fixedTag: Boolean

    @get:Schema(title = "菜单显示", description = "是否显示该菜单")
    val showLink: Boolean

    @get:Schema(title = "父级菜单显示", description = "是否显示父级菜单")
    val showParent: Boolean
}
