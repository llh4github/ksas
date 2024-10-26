package io.github.llh4github.ksas.service.auth

import io.github.llh4github.ksas.commons.PageQueryParam
import io.github.llh4github.ksas.commons.PageResult
import io.github.llh4github.ksas.dbmodel.auth.Role
import io.github.llh4github.ksas.service.BaseService
import org.babyfish.jimmer.Input
import org.babyfish.jimmer.sql.kt.ast.query.specification.KSpecification

interface RoleService : BaseService<Role> {

    /**
     * 新增角色
     *
     * `role.code` 存在时则抛出异常
     */
    fun addUnique(entity: Input<Role>): Role

    /**
     * 根据ID更新角色
     *
     * `role.code` 存在时则抛出异常
     */
    fun checkAndUpdateById(entity: Input<Role>): Role

    fun pageQuery(querySpec: KSpecification<Role>, pageQueryParam: PageQueryParam): PageResult<Role>
}
