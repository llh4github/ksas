package io.github.llh4github.ksas.dbmodel.auth

import io.github.llh4github.ksas.dbmodel.BaseModel
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import org.babyfish.jimmer.sql.*
import org.hibernate.validator.constraints.Length

@Entity
@Table(name = "auth_user")
interface User : BaseModel {

    @Key
    @get:Schema(description = "用户名")
    @get:Length(min = 1, max = 50, message = "用户名长度必须在{min}-{max}个字符之间")
    @get:NotBlank(message = "用户名不能为空")
    val username: String

    @get:Length(min = 1, max = 50, message = "密码长度必须在{min}-{max}个字符之间")
    @get:NotBlank(message = "密码不能为空")
    @get:Schema(description = "密码")
    val password: String

    @get:Length(min = 1, max = 20, message = "昵称长度必须在{min}-{max}个字符之间")
    @get:Schema(description = "昵称")
    @get:NotBlank(message = "昵称不能为空")
    val nickname: String

    @ManyToMany
    @JoinTable(
        name = "link_user_role",
        joinColumnName = "user_id",
        inverseJoinColumnName = "role_id"
    )
    val roles: List<Role>
}
