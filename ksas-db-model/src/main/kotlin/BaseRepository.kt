package io.github.llh4github.ksas.dbmodel

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.fetcher.Fetcher
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.KExecutable
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.babyfish.jimmer.sql.kt.ast.mutation.KMutableDelete
import org.babyfish.jimmer.sql.kt.ast.mutation.KMutableUpdate
import org.babyfish.jimmer.sql.kt.ast.mutation.KSimpleSaveResult
import org.babyfish.jimmer.sql.kt.ast.query.KConfigurableRootQuery
import org.babyfish.jimmer.sql.kt.ast.query.KMutableRootQuery
import kotlin.reflect.KClass


abstract class BaseRepository<E : BaseModel>(
    private val sqlClient: KSqlClient
) {
    abstract val entityType: KClass<E>

    fun <R> createQuery(
        block: KMutableRootQuery<E>.() -> KConfigurableRootQuery<E, R>
    ): KConfigurableRootQuery<E, R> =
        sqlClient.createQuery(entityType, block)

    fun createUpdate(
        block: KMutableUpdate<E>.() -> Unit
    ): KExecutable<Int> =
        sqlClient.createUpdate(entityType, block)

    fun createDelete(
        block: KMutableDelete<E>.() -> Unit
    ): KExecutable<Int> =
        sqlClient.createDelete(entityType, block)

    //region save or insert method

    fun insert(entity: E): KSimpleSaveResult<E> {
        return sqlClient.insert(entity)
    }
    fun update(entity: E): KSimpleSaveResult<E> {
        return sqlClient.update(entity)
    }

    //endregion

    //region query method

    fun findById(id: Long, fetcher: Fetcher<E>? = null): E? {
        return if (fetcher !== null) {
            sqlClient.findById(fetcher, id)
        } else {
            sqlClient.findById(entityType, id)
        }
    }

    fun <S : View<E>> findById(staticType: KClass<S>, id: Long): S? {
        return createQuery {
            where(table.getId<Long>() eq id)
            select(table.fetch(staticType))
        }.fetchOneOrNull()
    }

    fun findByIds(ids: List<Long>, fetcher: Fetcher<E>?): List<E> =
        if (fetcher !== null) {
            sqlClient.findByIds(fetcher, ids)
        } else {
            sqlClient.findByIds(entityType, ids)
        }

    fun <S : View<E>> findByIds(staticType: KClass<S>, ids: List<Long>): List<S> {
        return createQuery {
            where(table.getId<Long>() valueIn ids)
            select(table.fetch(staticType))
        }.execute()
    }

    //endregion

}
