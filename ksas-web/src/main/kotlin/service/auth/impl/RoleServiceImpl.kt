package io.github.llh4github.ksas.service.auth.impl

import io.github.llh4github.ksas.dbmodel.auth.Role
import io.github.llh4github.ksas.service.BaseServiceImpl
import io.github.llh4github.ksas.service.auth.RoleService
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class RoleServiceImpl(
    private val sqlClient: KSqlClient,
) : BaseServiceImpl<Role>(sqlClient), RoleService {
    override val entityType: KClass<Role> = Role::class

}
