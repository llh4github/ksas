package io.github.llh4github.ksas.service.auth.impl

import io.github.llh4github.ksas.dbmodel.auth.Role
import io.github.llh4github.ksas.dbmodel.auth.code
import io.github.llh4github.ksas.dbmodel.auth.id
import io.github.llh4github.ksas.exception.RoleModuleException
import io.github.llh4github.ksas.service.BaseServiceImpl
import io.github.llh4github.ksas.service.auth.RoleService
import org.babyfish.jimmer.Input
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.count
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@Service
class RoleServiceImpl(
    private val sqlClient: KSqlClient,
) : BaseServiceImpl<Role>(sqlClient), RoleService {
    override val entityType: KClass<Role> = Role::class

    @Transactional
    override fun addUnique(entity: Input<Role>): Role {
        val model = entity.toEntity()
        val count = createQuery {
            where(table.code eq model.code)
            select(count(table.id))
        }.fetchOne()
        if (count > 0) {
            throw RoleModuleException.roleCodeExist(message = "角色编码已存在", roleCode = model.code)
        }
        return insert(model).modifiedEntity
    }
}
