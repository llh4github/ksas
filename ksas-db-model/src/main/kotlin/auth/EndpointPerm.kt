package io.github.llh4github.ksas.dbmodel.auth

import io.github.llh4github.ksas.dbmodel.BaseModel
import io.github.llh4github.ksas.dbmodel.enums.RequestMethodEnum
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.Table


/**
 * 接口权限表
 */
@Entity
@Table(name = "auth_endpoint_perm")
interface EndpointPerm : BaseModel {

    val title: String

    val path: String

    val method: RequestMethodEnum

    val remark: String?

    @Key
    val permCode: String
}
