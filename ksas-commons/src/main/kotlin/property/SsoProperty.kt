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
     * 桶名
     */
    var bucket: String = "ksas"

    /**
     * 访问密钥
     */
    var accessKey: String = ""

    /**
     * 秘钥
     */
    var secretKey: String = ""
}