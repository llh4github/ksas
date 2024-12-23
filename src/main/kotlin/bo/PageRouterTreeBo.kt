package io.github.llh4github.ksas.bo

import com.fasterxml.jackson.annotation.JsonIgnore
import io.github.llh4github.ksas.dbmodel.auth.dto.PageRouterWithPermissionView
import io.github.llh4github.ksas.dbmodel.extra.PageRouterMeta

/**
 * 用于构建页面路由树形数据
 */
data class PageRouterTreeBo(
    val id: Long,
    val parentId: Long? = null,
    val meta: PageRouterMeta,
    val path: String,
    val name: String,
    val redirect: String? = null,
    val children: MutableList<PageRouterTreeBo> = mutableListOf(),
) {

    @get:JsonIgnore
    val permissionCodes = meta.permissions.map { it.code }

    companion object {
        fun from(model: PageRouterWithPermissionView): PageRouterTreeBo {
            return PageRouterTreeBo(
                id = model.id,
                parentId = model.parentId,
                meta = model.meta,
                name = model.name,
                path = model.path,
                redirect = model.redirect,
            )
        }
    }
}
