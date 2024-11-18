package io.github.llh4github.ksas.service.auth

import io.github.llh4github.ksas.bo.EndpointPermCheckBo
import io.github.llh4github.ksas.dbmodel.auth.User
import io.github.llh4github.ksas.dbmodel.auth.dto.UserAddInput
import io.github.llh4github.ksas.service.BaseService

interface UserService : BaseService<User> {
    fun addUnique(input: UserAddInput): User


    /**
     * 接口权限检查
     *
     * 此方法用于检查用户是否有权限访问某个接口。应该是在SpringSecurity中调用。
     * @param bo [EndpointPermCheckBo]
     * @return true 有权限 false 无权限
     */
    fun endpointPermCheck(bo: EndpointPermCheckBo): Boolean
}
