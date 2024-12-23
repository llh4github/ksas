package io.github.llh4github.ksas.commons.property

/**
 * SSO配置
 */
class SsoProperty {
    /**
     * SSO服务端地址
     */
    var endpoint: String = "http://127.0.0.1:9000"

    /**
     * 访问密钥
     */
    var accessKey: String = ""

    /**
     * 秘钥
     */
    var secretKey: String = ""

    /**
     * 资源访问基础路径
     */
    var baseUrl: String = "http://localhost:9000"
}
