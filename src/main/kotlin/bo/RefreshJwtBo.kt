package io.github.llh4github.ksas.bo

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

/**
 * 刷新jwt所需参数
 */
data class RefreshJwtBo(
    @Schema(description = "凭证")
    val accessToken: String,
    @Schema(description = "刷新凭证")
    val refreshToken: String,
)

data class RefreshResultBo(
    @Schema(description = "凭证")
    val accessToken: String,
    @Schema(description = "刷新凭证")
    val refreshToken: String,

    @Schema(description = "访问凭证过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val expire: Date,
)
