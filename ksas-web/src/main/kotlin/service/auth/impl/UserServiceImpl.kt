package io.github.llh4github.ksas.service.auth.impl

import io.github.llh4github.ksas.bo.EndpointPermCheckBo
import io.github.llh4github.ksas.bo.UserDetailBo
import io.github.llh4github.ksas.commons.property.WebSecurityProperty
import io.github.llh4github.ksas.dbmodel.auth.User
import io.github.llh4github.ksas.dbmodel.auth.dto.UserAddInput
import io.github.llh4github.ksas.dbmodel.auth.dto.UserEndpointPermView
import io.github.llh4github.ksas.dbmodel.auth.dto.UserRestPwdInput
import io.github.llh4github.ksas.dbmodel.auth.dto.UserUpdateRoleInput
import io.github.llh4github.ksas.dbmodel.auth.id
import io.github.llh4github.ksas.dbmodel.auth.username
import io.github.llh4github.ksas.exception.DbCommonException
import io.github.llh4github.ksas.service.BaseServiceImpl
import io.github.llh4github.ksas.service.CommonOperate
import io.github.llh4github.ksas.service.auth.UserService
import io.github.llh4github.ksas.service.testAddDbResult
import io.github.llh4github.ksas.service.testUpdateDbResult
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
import org.springframework.util.AntPathMatcher

@Service
class UserServiceImpl(private val sqlClient: KSqlClient) :
    BaseServiceImpl<User>(User::class, sqlClient),
    UserService,
    CommonOperate<User>,
    UserDetailsService {
    @Autowired
    private lateinit var property: WebSecurityProperty

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder
    private val pathMatcher by lazy { AntPathMatcher() }

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
                    fieldName = "username",
                    fieldValue = entity.username,
                )
            }
        }
    }

    @Transactional
    override fun addUnique(input: UserAddInput): User {
        val entity = input.toEntity {
            password = passwordEncoder.encode(input.password)
        }
        return addUniqueData(entity, sqlClient)
    }

    override fun updateRole(input: UserUpdateRoleInput): Boolean {
        val rs = sqlClient.save(input)
        testAddDbResult(rs)
        return rs.isModified
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = createQuery {
            where(table.username eq username)
            select(table)
        }.fetchOneOrNull() ?: throw UsernameNotFoundException("用户 $username 不存在")
        return UserDetailBo(user)
    }

    @Suppress("ReturnCount")
    override fun endpointPermCheck(bo: EndpointPermCheckBo): Boolean {
        if (property.commonUrls.any { pathMatcher.match(it, bo.uri) }) {
            return true
        }
        val user = createQuery {
            where(table.username eq bo.username)
            select(table.fetch(UserEndpointPermView::class))
        }.fetchOneOrNull() ?: return false
        return user.roles.flatMap { it.endpointPerms }
            .any {
                it.method.match(bo.method) && pathMatcher.match(it.path, bo.uri)
            }
    }

    override fun updatePwd(input: UserRestPwdInput): Boolean {
        val entity = input.toEntity {
            password = passwordEncoder.encode(input.password)
        }
        val rs = sqlClient.update(entity)
        testUpdateDbResult(rs)
        return rs.isModified
    }
}
