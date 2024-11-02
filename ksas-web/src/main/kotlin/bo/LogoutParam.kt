package io.github.llh4github.ksas.bo

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "登出参数")
data class LogoutParam(
    @Schema(description = "凭证")
    val accessToken: String,
    @Schema(description = "刷新凭证")
    val refreshToken: String,
)
