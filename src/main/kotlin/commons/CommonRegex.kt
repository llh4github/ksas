package io.github.llh4github.ksas.commons

/**
 * 常用正则式
 */
object CommonRegex {
    const val PHONE = "^1[3-9]\\d{9}$"
    const val EMAIL = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$"
    const val ID_CARD = "^\\d{17}[0-9Xx]$"

    /**
     * 权限码只能包含字母、数字、英文冒号和星号
     */
    const val PERMISSION_CODE = "^[a-zA-Z0-9:*]+$"
}
