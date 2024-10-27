package io.github.llh4github.ksas.service

import io.github.llh4github.ksas.dbmodel.BaseModel
import org.babyfish.jimmer.Input

interface UniqueDataUpsert<E : BaseModel> {

    /**
     * 新增实体
     *
     * `entity` 的唯一键存在时则抛出异常
     */
    fun addUnique(entity: Input<E>): E

    /**
     * 根据ID更新实体
     *
     * `entity` 的唯一键存在时则抛出异常
     */
    fun checkAndUpdateById(entity: Input<E>): E
}
