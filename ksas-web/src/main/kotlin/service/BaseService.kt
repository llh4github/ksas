package io.github.llh4github.ksas.service

import io.github.llh4github.ksas.dbmodel.BaseModel
import org.babyfish.jimmer.Input
import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.fetcher.Fetcher
import kotlin.reflect.KClass

interface BaseService<E : BaseModel> {

    fun save(entity: E): E
    fun save(entity: Input<E>): E = save(entity.toEntity())

    fun updateById(entity: E): E

    fun updateById(entity: Input<E>): E = updateById(entity.toEntity())

    fun getById(id: Long): E?
    fun getByIds(ids: List<Long>): List<E>

    fun <S : View<E>> getById(staticType: KClass<S>, id: Long): S?
    fun <S : View<E>> getByIds(staticType: KClass<S>, ids: List<Long>): List<S>

    @Deprecated("Use updateById instead")
    fun getById(id: Long, fetcher: Fetcher<E>? = null): E?

    @Deprecated("Use updateById instead")
    fun getByIds(ids: List<Long>, fetcher: Fetcher<E>? = null): List<E>

    /**
     * 移除数据。真实删除或逻辑删除由具体配置决定。
     */
    fun removeByIds(ids: List<Long>): Boolean

    fun removeById(id: Long): Boolean = removeByIds(listOf(id))

}
