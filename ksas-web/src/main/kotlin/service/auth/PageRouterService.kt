package io.github.llh4github.ksas.service.auth

import io.github.llh4github.ksas.bo.PageRouterTreeBo
import io.github.llh4github.ksas.dbmodel.auth.PageRouter
import io.github.llh4github.ksas.dbmodel.auth.dto.PageRouterAddInput
import io.github.llh4github.ksas.dbmodel.auth.dto.PageRouterCascaderView
import io.github.llh4github.ksas.dbmodel.auth.dto.PageRouterPermissionUpdateInput
import io.github.llh4github.ksas.dbmodel.auth.dto.PageRouterUpdateInput
import io.github.llh4github.ksas.service.BaseService
import io.github.llh4github.ksas.service.CommonOperate

interface PageRouterService : BaseService<PageRouter>, CommonOperate<PageRouter> {
    /**
     * 获取所有路由树
     */
    fun allRouterTree(permissions: List<String>):List<PageRouterTreeBo>

    fun addUnique(input: PageRouterAddInput): PageRouter
    fun updateUnique(input: PageRouterUpdateInput): PageRouter

    /**
     * 级联选择数据
     */
    fun cascader(): List<PageRouterCascaderView>

    fun updatePermission(input: PageRouterPermissionUpdateInput): Boolean
}
