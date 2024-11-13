package io.github.llh4github.ksas.dbmodel.auth

import io.github.llh4github.ksas.dbmodel.BaseModel
import jakarta.validation.constraints.NotBlank
import org.babyfish.jimmer.sql.*
import org.hibernate.validator.constraints.Length

@Entity
@Table(name = "auth_role")
interface Role : BaseModel {

    @get:Length(min = 1, max = 50, message = "title长度必须在{min}-{max}个字符之间")
    @get:NotBlank(message = "title不能为空")
    val title: String

    @Key
    @get:Length(min = 1, max = 50, message = "code长度必须在{min}-{max}个字符之间")
    @get:NotBlank(message = "code不能为空")
    val code: String

    @ManyToMany(mappedBy = "roles")
    val users: List<User>

    @ManyToMany
    @JoinTable(
        name = "link_role_endpoint",
        joinColumnName = "role_id",
        inverseJoinColumnName = "endpoint_id"
    )
    val endpointPerms: List<EndpointPerm>


    @ManyToMany
    @JoinTable(
        name = "link_role_page_router",
        joinColumnName = "role_id",
        inverseJoinColumnName = "page_router_id"
    )
    val pageRouters: List<PageRouter>
}
