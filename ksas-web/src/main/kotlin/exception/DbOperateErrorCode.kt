package io.github.llh4github.ksas.exception

import org.babyfish.jimmer.error.ErrorFamily

@ErrorFamily
enum class CommonModuleErrorCode {
    /**
     * 新增数据失败
     */
    ADD_FAILED,

    /**
     * 更新数据失败
     */
    UPDATE_FAILED,
}
