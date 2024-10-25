package io.github.llh4github.ksas.service

import io.github.llh4github.ksas.dbmodel.BaseModel
import io.github.llh4github.ksas.dbmodel.BaseRepository
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.transaction.annotation.Transactional

abstract class BaseServiceImpl<E : BaseModel>(
    private val sqlClient: KSqlClient
) : BaseRepository<E>(sqlClient), BaseService<E> {

    @Transactional
    override fun updateById(entity: E): E {
        return update(entity).modifiedEntity
    }

    @Transactional
    override fun save(entity: E): E {
        return insert(entity).modifiedEntity
    }

    override fun getById(id: Long): E? {
        return findById(id)
    }
}
