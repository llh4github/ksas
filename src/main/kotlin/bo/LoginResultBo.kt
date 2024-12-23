package io.github.llh4github.ksas.bo

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "登录结果业务对象")
data class LoginResultBo(
    @Schema(description = "用户ID")
    val userId: Long,
    @Schema(description = "用户名")
    val username: String,
    @Schema(description = "访问凭证")
    val accessToken: String,
    @Schema(description = "刷新凭证")
    val refreshToken: String,
    /**
     * 访问凭证过期时间。减少前端计算
     */
    @Schema(description = "访问凭证过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val expire: Date,
    @Schema(description = "角色code列表")
    val roles: List<String> = emptyList(),
    @Schema(description = "权限列表")
    val permissions: List<String> = emptyList(),
)
