package io.github.llh4github.ksas.commons.property

class WebSecurityProperty {
    /**
     * 可匿名访问的URL
     */
    var anonUrls: Set<String> = setOf()

    /**
     * JWT配置
     */
    var jwt = JwtProperty()

    /**
     * JWT请求头名称
     */
    var jwtHeaderName = "Authorization"

    /**
     * JWT请求头前缀
     */
    var jwtHeaderPrefix = "Bearer "

    /**
     * 是否启用API文档
     */
    var enableApiDoc = false

    /**
     * 缓存Jwt键名前缀
     */
    var cacheJwtPrefix: String = "ksas:jwt:"
}
