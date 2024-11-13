package io.github.llh4github.ksas.service.auth.impl

import io.github.llh4github.ksas.bo.UserDetailBo
import io.github.llh4github.ksas.commons.PageQueryParam
import io.github.llh4github.ksas.commons.PageResult
import io.github.llh4github.ksas.dbmodel.auth.User
import io.github.llh4github.ksas.dbmodel.auth.dto.UserAddInput
import io.github.llh4github.ksas.dbmodel.auth.dto.UserBaseView
import io.github.llh4github.ksas.dbmodel.auth.id
import io.github.llh4github.ksas.dbmodel.auth.username
import io.github.llh4github.ksas.exception.UserModuleException
import io.github.llh4github.ksas.service.BaseServiceImpl
import io.github.llh4github.ksas.service.auth.UserService
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.count
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.query.specification.KSpecification
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
    UserService, UserDetailsService {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Transactional
    override fun addUnique(input: UserAddInput): User {
        createQuery {
            where(table.username eq input.username)
            select(count(table.id))
        }.fetchOne().let {
            if (it > 0) {
                throw UserModuleException.usernameExists(
                    message = "用户名已存在",
                    username = input.username
                )
            }
        }

        val model = input.toEntity {
            password = passwordEncoder.encode(input.password)
        }
        val rs = sqlClient.insert(model)
        testAddDbResult(rs)
        return rs.modifiedEntity
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = createQuery {
            where(table.username eq username)
            select(table)
        }.fetchOneOrNull() ?: throw UsernameNotFoundException("用户 $username 不存在")
        return UserDetailBo(user)
    }

    override fun pageQuery(
        querySpec: KSpecification<User>,
        pageQueryParam: PageQueryParam
    ): PageResult<UserBaseView> {
        return createQuery {
            where(querySpec)
            select(table.fetch(UserBaseView::class))
        }.fetchCustomPage(pageQueryParam)
    }
}
