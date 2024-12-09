package io.github.llh4github.ksas.service.auth

import io.github.llh4github.ksas.dbmodel.auth.EndpointPerm
import io.github.llh4github.ksas.dbmodel.auth.dto.EndpointPermAddInput
import io.github.llh4github.ksas.dbmodel.auth.dto.EndpointPermTreeView
import io.github.llh4github.ksas.dbmodel.auth.dto.EndpointPermUpdateInput
import io.github.llh4github.ksas.service.BaseService

interface EndpointPermService : BaseService<EndpointPerm> {

    fun addUnique(input: EndpointPermAddInput): EndpointPerm

    fun updateUnique(input: EndpointPermUpdateInput): EndpointPerm

    /**
     * 获取权限树
     * @param id Long? 节点id。如果为null，则获取整个树
     */
    fun fetchTree(id: Long? = null): EndpointPermTreeView?
}
