package io.github.llh4github.ksas.service

import io.github.llh4github.ksas.commons.PageQueryParam
import io.github.llh4github.ksas.commons.PageQueryParamTrait
import io.github.llh4github.ksas.commons.PageResult
import io.github.llh4github.ksas.dbmodel.BaseModel
import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.KExecutable
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.babyfish.jimmer.sql.kt.ast.mutation.KMutableDelete
import org.babyfish.jimmer.sql.kt.ast.mutation.KMutableUpdate
import org.babyfish.jimmer.sql.kt.ast.query.KConfigurableRootQuery
import org.babyfish.jimmer.sql.kt.ast.query.KMutableRootQuery
import org.babyfish.jimmer.sql.kt.ast.query.specification.KSpecification
import org.babyfish.jimmer.sql.kt.ast.table.makeOrders
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionTemplate
import kotlin.reflect.KClass

@Suppress("TooManyFunctions")
abstract class BaseServiceImpl<E : BaseModel>(
    private val entityType: KClass<E>,
    private val sqlClient: KSqlClient
) : BaseService<E> {

    @Autowired
    protected lateinit var transactionTemplate: TransactionTemplate

    protected fun <R> createQuery(
        block: KMutableRootQuery<E>.() -> KConfigurableRootQuery<E, R>
    ): KConfigurableRootQuery<E, R> =
        sqlClient.createQuery(entityType, block)

    protected fun createUpdate(
        block: KMutableUpdate<E>.() -> Unit
    ): KExecutable<Int> =
        sqlClient.createUpdate(entityType, block)

    protected fun createDelete(
        block: KMutableDelete<E>.() -> Unit
    ): KExecutable<Int> =
        sqlClient.createDelete(entityType, block)

    @Transactional
    override fun updateById(entity: E): E {
        val rs = sqlClient.update(entity)
        testUpdateDbResult(rs)
        return rs.modifiedEntity
    }

    @Transactional
    override fun save(entity: E): E {
        val rs = sqlClient.update(entity)
        testAddDbResult(rs)
        return rs.modifiedEntity
    }


    override fun <S : View<E>> getById(staticType: KClass<S>, id: Long): S? {
        return createQuery {
            where(table.getId<Long>() eq id)
            select(table.fetch(staticType))
        }.fetchOneOrNull()
    }


    override fun <S : View<E>> getByIds(staticType: KClass<S>, ids: List<Long>): List<S> {
        return createQuery {
            where(table.getId<Long>() valueIn ids)
            select(table.fetch(staticType))
        }.execute()
    }

    override fun getById(id: Long): E? {
        return sqlClient.findById(entityType, id)
    }

    override fun getByIds(ids: List<Long>): List<E> {
        return sqlClient.findByIds(entityType, ids)
    }

    override fun <S : View<E>> pageQuery(
        staticType: KClass<S>,
        querySpec: KSpecification<E>,
        pageQueryParam: PageQueryParam,
        sortField: String,
    ): PageResult<S> {
        return createQuery {
            orderBy(table.makeOrders(sortField))
            where(querySpec)
            select(table.fetch(staticType))
        }.fetchCustomPage(pageQueryParam)
    }

    override fun <S : View<E>> listQuery(
        staticType: KClass<S>,
        querySpec: KSpecification<E>?,
        sortField: String,
        limit: Int?,
    ): List<S> {
        val condition = createQuery {
            orderBy(table.makeOrders(sortField))
            querySpec?.let {
                where(querySpec)
            }
            select(table.fetch(staticType))
        }
        if (limit == null) {
            return condition.execute()
        }
        return condition.limit(limit).execute()
    }

    override fun removeByIds(ids: List<Long>): Boolean {
        val rs = sqlClient.deleteById(entityType, ids)
        testDeleteDbResult(rs)
        return true
    }


    protected fun <R> KConfigurableRootQuery<E, R>.fetchCustomPage(
        pageParam: PageQueryParamTrait
    ): PageResult<R> {
        val rs = fetchPage(pageParam.pageNum(), pageParam.pageSize)
        return PageResult(
            totalRowCount = rs.totalRowCount,
            totalPage = rs.totalPageCount,
            records = rs.rows
        )
    }

    companion object {


    }
}