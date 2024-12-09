package io.github.llh4github.ksas.dbmodel.auth

import io.github.llh4github.ksas.commons.CommonRegex
import io.github.llh4github.ksas.dbmodel.BaseModel
import io.github.llh4github.ksas.dbmodel.enums.RequestMethodEnum
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.babyfish.jimmer.sql.*
import org.hibernate.validator.constraints.Length


/**
 * 接口权限表
 */
@Entity
@Table(name = "auth_endpoint_perm")
interface EndpointPerm : BaseModel {

    @get:Length(min = 1, max = 50, message = "接口名长度必须在{min}-{max}个字符之间")
    @get:NotBlank(message = "接口名不能为空")
    @get:Schema(description = "接口名")
    val title: String

    @get:Pattern(regexp = "^/.+", message = "路径必须以'/'开头")
    @get:Length(min = 2, max = 100, message = "路径长度必须在{min}-{max}个字符之间")
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
    @get:Pattern(regexp = CommonRegex.PERMISSION_CODE, message = "权限码只能包含字母、数字、英文冒号和星号")
    @get:Schema(description = "权限码")
    val permCode: String

    @ManyToOne
    val parent: EndpointPerm?

    @OneToMany(mappedBy = "parent")
    val children: List<EndpointPerm>
}
