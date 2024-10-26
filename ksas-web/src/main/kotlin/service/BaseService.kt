package io.github.llh4github.ksas.service

import io.github.llh4github.ksas.dbmodel.BaseModel
import org.babyfish.jimmer.Input

interface BaseService<E : BaseModel> {

    fun save(entity: E): E
    fun save(entity: Input<E>): E = save(entity.toEntity())

    fun updateById(entity: E): E
    fun updateById(entity: Input<E>): E = updateById(entity.toEntity())

    fun getById(id: Long): E?
}
