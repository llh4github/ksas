package io.github.llh4github.ksas.service.auth.impl

import io.github.llh4github.ksas.bo.UserDetailBo
import io.github.llh4github.ksas.dbmodel.auth.User
import io.github.llh4github.ksas.dbmodel.auth.dto.UserAddInput
import io.github.llh4github.ksas.dbmodel.auth.id
import io.github.llh4github.ksas.dbmodel.auth.username
import io.github.llh4github.ksas.exception.DbCommonException
import io.github.llh4github.ksas.service.BaseServiceImpl
import io.github.llh4github.ksas.service.CommonOperate
import io.github.llh4github.ksas.service.auth.UserService
import org.babyfish.jimmer.kt.isLoaded
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.count
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.ne
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(private val sqlClient: KSqlClient) :
    BaseServiceImpl<User>(User::class, sqlClient),
    UserService,
    CommonOperate<User>,
    UserDetailsService {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    override fun checkUnique(entity: User) {
        createQuery {
            where(table.username eq entity.username)
            if (isLoaded(entity, User::id)) {
                where(table.id ne entity.id)
            }
            select(count(table.id))
        }.fetchOne().let {
            if (it > 0) {
                throw DbCommonException.dataExists(
                    message = "用户名已存在",
                    fieldName = "code",
                    fieldValue = entity.username,
                )
            }
        }
    }

    @Transactional
    override fun addUnique(input: UserAddInput): User {
        val model = input.toEntity {
            password = passwordEncoder.encode(input.password)
        }
        return checkUnique(model) {
            val rs = sqlClient.insert(model)
            testAddDbResult(rs)
            rs.modifiedEntity
        }
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = createQuery {
            where(table.username eq username)
            select(table)
        }.fetchOneOrNull() ?: throw UsernameNotFoundException("用户 $username 不存在")
        return UserDetailBo(user)
    }

}
