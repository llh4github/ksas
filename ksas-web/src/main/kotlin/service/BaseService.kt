package io.github.llh4github.ksas.service

import io.github.llh4github.ksas.dbmodel.BaseModel

interface BaseService<E: BaseModel> {

    fun save(entity: E): E

    fun updateById(entity: E): E

    fun getById(id: Long): E?
}
