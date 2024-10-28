package io.github.llh4github.ksas.service.auth.impl

import io.github.llh4github.ksas.dbmodel.auth.User
import io.github.llh4github.ksas.dbmodel.auth.id
import io.github.llh4github.ksas.dbmodel.auth.username
import io.github.llh4github.ksas.exception.UserModuleException
import io.github.llh4github.ksas.service.BaseServiceImpl
import io.github.llh4github.ksas.service.auth.UserService
import org.babyfish.jimmer.Input
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(private val sqlClient: KSqlClient) :
    BaseServiceImpl<User>(User::class, sqlClient), UserService {

    @Transactional
    override fun addUnique(entity: Input<User>): User {
        val model = entity.toEntity()
        createQuery {
            where(table.username eq model.username)
            select(table.id)
        }.fetchOne().let {
            if (it > 0) {
                throw UserModuleException.usernameExists(
                    message = "用户名已存在",
                    username = model.username
                )
            }
        }
        val rs = insert(model)
        checkAddResult(rs)
        return rs.modifiedEntity
    }

    @Transactional
    override fun checkAndUpdateById(entity: Input<User>): User {
        val model = entity.toEntity()
        createQuery {
            where(table.username eq model.username)
            where(table.id eq model.id)
            select(table.id)
        }.fetchOne().let {
            if (it > 0) {
                throw UserModuleException.usernameExists(
                    message = "用户名已存在",
                    username = model.username
                )
            }
        }
        val rs = update(model)
        checkUpdateDbResult(rs)
        return rs.modifiedEntity
    }
}
