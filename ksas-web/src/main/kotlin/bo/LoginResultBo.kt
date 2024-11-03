package io.github.llh4github.ksas.bo

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "登录结果业务对象")
data class LoginResultBo(
    @Schema(description = "用户ID")
    val userId: Long,
    @Schema(description = "用户名")
    val username: String,
    @Schema(description = "凭证")
    val accessToken: String,
    @Schema(description = "刷新凭证")
    val refreshToken: String,
)
