package io.github.llh4github.ksas.service.auth.impl

import io.github.llh4github.ksas.dbmodel.auth.Role
import io.github.llh4github.ksas.dbmodel.auth.code
import io.github.llh4github.ksas.dbmodel.auth.dto.RoleAddInput
import io.github.llh4github.ksas.dbmodel.auth.dto.RoleUpdateInput
import io.github.llh4github.ksas.dbmodel.auth.id
import io.github.llh4github.ksas.exception.DbCommonException
import io.github.llh4github.ksas.service.BaseServiceImpl
import io.github.llh4github.ksas.service.auth.RoleService
import org.babyfish.jimmer.kt.isLoaded
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.count
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.ne
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoleServiceImpl(
    private val sqlClient: KSqlClient,
) : BaseServiceImpl<Role>(Role::class, sqlClient), RoleService {

    override fun checkUnique(entity: Role) {
        val count = createQuery {
            where(table.code eq entity.code)
            if (isLoaded(entity, Role::id)) {
                where(table.id ne entity.id)
            }
            select(count(table.id))
        }.fetchOne()
        if (count > 0) {
            throw DbCommonException.dataExists(
                message = "角色编码已存在",
                fieldName = "code",
                fieldValue = entity.code,
            )
        }
    }

    @Transactional
    override fun addUnique(input: RoleAddInput): Role {
        val model = input.toEntity()
        return checkUnique(model) {
            val rs = sqlClient.insert(model)
            testAddDbResult(rs)
            rs.modifiedEntity
        }
    }

    @Transactional
    override fun checkAndUpdateById(input: RoleUpdateInput): Role {
        val model = input.toEntity()
        return checkUnique(model) {
            val rs = sqlClient.update(model)
            testUpdateDbResult(rs)
            rs.modifiedEntity
        }
    }

}
