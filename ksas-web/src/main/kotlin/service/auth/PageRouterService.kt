package io.github.llh4github.ksas.service.auth

import io.github.llh4github.ksas.dbmodel.auth.PageRouter
import io.github.llh4github.ksas.dbmodel.auth.dto.PageRouterAddInput
import io.github.llh4github.ksas.dbmodel.auth.dto.PageRouterTreeView
import io.github.llh4github.ksas.dbmodel.auth.dto.PageRouterUpdateInput
import io.github.llh4github.ksas.service.BaseService
import io.github.llh4github.ksas.service.CommonOperate

interface PageRouterService : BaseService<PageRouter>, CommonOperate<PageRouter> {
    /**
     * 获取所有路由树
     */
    fun allRouterTree(): List<PageRouterTreeView>

    fun addUnique(input: PageRouterAddInput): PageRouter
    fun updateUnique(input: PageRouterUpdateInput): PageRouter
}
