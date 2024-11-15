package io.github.llh4github.ksas.dbmodel.auth

import io.github.llh4github.ksas.dbmodel.BaseModel
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import org.babyfish.jimmer.sql.*
import org.hibernate.validator.constraints.Length

@Entity
@Table(name = "auth_role")
interface Role : BaseModel {

    @get:Length(min = 1, max = 50, message = "角色名长度必须在{min}-{max}个字符之间")
    @get:NotBlank(message = "角色名称不能为空")
    val title: String

    @Key
    @get:Length(min = 1, max = 50, message = "角色码长度必须在{min}-{max}个字符之间")
    @get:NotBlank(message = "角色码不能为空")
    val code: String

    @ManyToMany(mappedBy = "roles")
    val users: List<User>

    @ManyToMany
    @JoinTable(
        name = "link_role_endpoint",
        joinColumnName = "role_id",
        inverseJoinColumnName = "endpoint_id"
    )
    @Deprecated("暂只考虑角色-页面-接口权限的关联")
    val endpointPerms: List<EndpointPerm>

    @ManyToMany
    @JoinTable(
        name = "link_role_page_router",
        joinColumnName = "role_id",
        inverseJoinColumnName = "page_router_id"
    )
    @get:Schema(description = "角色关联的页面路由")
    val pageRouters: List<PageRouter>
}
