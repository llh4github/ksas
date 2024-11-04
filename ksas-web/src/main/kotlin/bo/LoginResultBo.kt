package io.github.llh4github.ksas.bo

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
    val expire: Date
)
