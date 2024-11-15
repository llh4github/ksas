package io.github.llh4github.ksas.dbmodel.auth

import io.github.llh4github.ksas.dbmodel.BaseModel
import io.github.llh4github.ksas.dbmodel.enums.RequestMethodEnum
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.Table
import org.hibernate.validator.constraints.Length


/**
 * 接口权限表
 */
@Entity
@Table(name = "auth_endpoint_perm")
interface EndpointPerm : BaseModel {

    @get:Length(min = 1, max = 50, message = "标题长度必须在{min}-{max}个字符之间")
    @get:NotBlank(message = "标题不能为空")
    @get:Schema(description = "标题")
    val title: String

    @get:Pattern(regexp = "^/.+", message = "路径必须以'/'开头")
    @get:Length(min = 2, max = 50, message = "路径长度必须在{min}-{max}个字符之间")
    @get:NotBlank(message = "路径不能为空")
    @get:Schema(description = "路径")
    val path: String

    @get:Schema(description = "请求方法")
    val method: RequestMethodEnum

    @get:Schema(description = "备注")
    @get:Length(max = 150, message = "备注最多{max}个字符")
    val remark: String?

    @Key
    @get:Length(min = 1, max = 50, message = "权限码长度必须在{min}-{max}个字符之间")
    @get:NotBlank(message = "权限码不能为空")
    @get:Schema(description = "权限码")
    val permCode: String
}
