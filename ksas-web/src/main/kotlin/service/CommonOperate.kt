package io.github.llh4github.ksas.service

import io.github.llh4github.ksas.dbmodel.BaseModel
import org.babyfish.jimmer.Input

/**
 * 通用业务方法
 */
interface CommonOperate<E : BaseModel> :
    UniqueDataChecker<E>


interface UniqueDataChecker<E : BaseModel> {

    fun checkUnique(entity: Input<E>) = checkUnique(entity.toEntity())

    /**
     * 检查唯一性，如果不唯一则抛出异常。
     * 只考虑主要字段的唯一性，其他字段的唯一性需要在具体的业务方法中处理
     */
    fun checkUnique(entity: E)

    fun checkUnique(entity: E, block: () -> E): E {
        checkUnique(entity)
        return block()
    }

}
