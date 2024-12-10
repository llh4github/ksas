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
    @get:Schema(description = "角色名称")
    val title: String

    @Key
    @get:Length(min = 1, max = 50, message = "角色码长度必须在{min}-{max}个字符之间")
    @get:NotBlank(message = "角色码不能为空")
    @get:Schema(description = "角色码")
    val code: String

    @get:Length(max = 200, message = "备注最多{max}个字符")
    @get:Schema(description = "备注")
    val remark: String

    @ManyToMany(mappedBy = "roles")
    val users: List<User>

    @ManyToMany
    @JoinTable(
        name = "link_role_endpoint",
        joinColumnName = "role_id",
        inverseJoinColumnName = "endpoint_id"
    )
    @Deprecated("应该只考虑角色与权限的关系，拥有的相同的权限即可进入对应的菜单,稍后删除")
    val endpointPerms: List<EndpointPerm>

    @ManyToMany
    @JoinTable(
        name = "link_role_permission",
        joinColumnName = "role_id",
        inverseJoinColumnName = "permission_id"
    )
    val permissions: List<Permission>

    @ManyToMany
    @JoinTable(
        name = "link_role_page_router",
        joinColumnName = "role_id",
        inverseJoinColumnName = "page_router_id"
    )
    @get:Schema(description = "角色关联的页面路由")
    @Deprecated("应该只考虑角色与权限的关系，拥有的相同的权限即可进入对应的菜单,稍后删除")
    val pageRouters: List<PageRouter>
}
