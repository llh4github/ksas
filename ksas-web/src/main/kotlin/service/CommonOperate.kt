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

    fun checkUnique(entity: E)

    fun checkUnique(entity: E, block: () -> E): E {
        checkUnique(entity)
        return block()
    }

}
