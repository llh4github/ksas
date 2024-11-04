package io.github.llh4github.ksas.dbmodel.auth

import io.github.llh4github.ksas.dbmodel.BaseModel
import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "auth_role")
interface Role : BaseModel {

    val title: String

    @Key
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
}
