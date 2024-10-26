package io.github.llh4github.ksas.dbmodel.auth

import io.github.llh4github.ksas.dbmodel.BaseModel
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "auth_role")
interface Role : BaseModel {

    val title: String

    @Key
    val code: String
}