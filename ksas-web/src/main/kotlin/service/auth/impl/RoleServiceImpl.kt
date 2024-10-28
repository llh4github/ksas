package io.github.llh4github.ksas.service.auth.impl

import io.github.llh4github.ksas.commons.PageQueryParam
import io.github.llh4github.ksas.commons.PageResult
import io.github.llh4github.ksas.dbmodel.auth.Role
import io.github.llh4github.ksas.dbmodel.auth.code
import io.github.llh4github.ksas.dbmodel.auth.dto.RoleBaseView
import io.github.llh4github.ksas.dbmodel.auth.id
import io.github.llh4github.ksas.exception.RoleModuleException
import io.github.llh4github.ksas.service.BaseServiceImpl
import io.github.llh4github.ksas.service.auth.RoleService
import org.babyfish.jimmer.Input
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.count
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.ne
import org.babyfish.jimmer.sql.kt.ast.query.specification.KSpecification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoleServiceImpl(
    private val sqlClient: KSqlClient,
) : BaseServiceImpl<Role>(Role::class, sqlClient), RoleService {

    @Transactional
    override fun addUnique(entity: Input<Role>): Role {
        val model = entity.toEntity()
        val count = createQuery {
            where(table.code eq model.code)
            select(count(table.id))
        }.fetchOne()
        if (count > 0) {
            throw RoleModuleException.roleCodeExist(
                message = "角色编码已存在",
                roleCode = model.code
            )
        }
        val rs = insert(model)
        checkAddResult(rs)
        return rs.modifiedEntity
    }

    @Transactional
    override fun checkAndUpdateById(entity: Input<Role>): Role {
        val model = entity.toEntity()
        val count = createQuery {
            where(table.code eq model.code)
            where(table.id ne model.id)
            select(count(table.id))
        }.fetchOne()
        if (count > 0) {
            throw RoleModuleException.roleCodeExist(
                message = "角色编码已存在",
                roleCode = model.code
            )
        }
        val rs = update(model)
        checkUpdateDbResult(rs)
        return rs.modifiedEntity
    }

    override fun pageQuery(
        querySpec: KSpecification<Role>,
        pageQueryParam: PageQueryParam
    ): PageResult<RoleBaseView> {
        return createQuery {
            where(querySpec)
            select(table.fetch(RoleBaseView::class))
        }.fetchCustomPage(pageQueryParam)
    }
}
