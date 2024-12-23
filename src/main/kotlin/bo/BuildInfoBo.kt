package io.github.llh4github.ksas.bo

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "构建信息")
data class BuildInfoBo(
    @Schema(description = "构建时间")
    val buildTime: String,
    @Schema(description = "构建版本")
    val version: String,
)
