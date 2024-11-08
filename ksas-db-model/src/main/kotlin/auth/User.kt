package io.github.llh4github.ksas.dbmodel.auth

import io.github.llh4github.ksas.dbmodel.BaseModel
import io.swagger.v3.oas.annotations.media.Schema
import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "auth_user")
interface User : BaseModel {

    @Key
    @get:Schema(description = "用户名")
    val username: String

    @get:Schema(description = "密码")
    val password: String

    @get:Schema(description = "昵称")
    val nickname: String

    @ManyToMany
    @JoinTable(
        name = "link_user_role",
        joinColumnName = "user_id",
        inverseJoinColumnName = "role_id"
    )
    val roles: List<Role>
}
