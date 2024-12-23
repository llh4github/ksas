package io.github.llh4github.ksas.bo

/**
 * 接口权限检查参数
 */
data class EndpointPermCheckBo(
    val username: String,
    val method: String,
    val uri: String,
)
