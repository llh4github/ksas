package io.github.llh4github.ksas.service.auth

import io.github.llh4github.ksas.commons.PageQueryParam
import io.github.llh4github.ksas.commons.PageResult
import io.github.llh4github.ksas.dbmodel.auth.Role
import io.github.llh4github.ksas.dbmodel.auth.dto.RoleBaseView
import io.github.llh4github.ksas.service.BaseService
import io.github.llh4github.ksas.service.CommonOperate
import org.babyfish.jimmer.sql.kt.ast.query.specification.KSpecification

interface RoleService : CommonOperate<Role>, BaseService<Role> {

    fun pageQuery(
        querySpec: KSpecification<Role>,
        pageQueryParam: PageQueryParam
    ): PageResult<RoleBaseView>
}
