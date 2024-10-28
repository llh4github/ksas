package io.github.llh4github.ksas.service.auth

import io.github.llh4github.ksas.commons.PageQueryParam
import io.github.llh4github.ksas.commons.PageResult
import io.github.llh4github.ksas.dbmodel.auth.User
import io.github.llh4github.ksas.dbmodel.auth.dto.UserBaseView
import io.github.llh4github.ksas.service.BaseService
import io.github.llh4github.ksas.service.UniqueDataUpsert
import org.babyfish.jimmer.sql.kt.ast.query.specification.KSpecification

interface UserService : UniqueDataUpsert<User>, BaseService<User> {

    fun pageQuery(
        querySpec: KSpecification<User>,
        pageQueryParam: PageQueryParam
    ): PageResult<UserBaseView>
}
