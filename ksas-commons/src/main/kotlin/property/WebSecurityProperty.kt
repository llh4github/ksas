package io.github.llh4github.ksas.commons.property

class WebSecurityProperty {
    /**
     * 可匿名访问的URL
     */
    var anonUrls: Set<String> = setOf()

    /**
     * 通用URL。所有用户都不需要进行权限检查。
     */
    var commonUrls :Set<String> = setOf()
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
     * 缓存Jwt键名前缀
     *
     * 不以冒号结尾
     */
    var cacheJwtPrefix: String = "ksas:jwt"
}
