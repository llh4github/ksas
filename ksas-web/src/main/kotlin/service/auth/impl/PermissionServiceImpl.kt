package io.github.llh4github.ksas.service.auth.impl

import io.github.llh4github.ksas.dbmodel.auth.Permission
import io.github.llh4github.ksas.dbmodel.auth.code
import io.github.llh4github.ksas.dbmodel.auth.id
import io.github.llh4github.ksas.exception.DbCommonException
import io.github.llh4github.ksas.service.BaseServiceImpl
import io.github.llh4github.ksas.service.UniqueDataChecker
import io.github.llh4github.ksas.service.auth.PermissionService
import org.babyfish.jimmer.kt.isLoaded
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.count
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.ne
import org.springframework.stereotype.Service

@Service
class PermissionServiceImpl(
    private val sqlClient: KSqlClient
) : PermissionService,
    UniqueDataChecker<Permission>,
    BaseServiceImpl<Permission>(Permission::class, sqlClient) {

    override fun checkUnique(entity: Permission) {
        createQuery {
            where(table.code eq entity.code)
            if (isLoaded(entity, Permission::id)) {
                where(table.id ne entity.id)
            }
            select(count(table.id))
        }.fetchOne().let { count ->
            if (count > 0) {
                throw DbCommonException.dataExists(
                    message = "权限编码已存在",
                    fieldName = "code",
                    fieldValue = entity.code,
                )
            }
        }
    }

}
