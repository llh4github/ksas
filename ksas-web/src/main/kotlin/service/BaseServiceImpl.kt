package io.github.llh4github.ksas.service

import io.github.llh4github.ksas.commons.PageQueryParamTrait
import io.github.llh4github.ksas.commons.PageResult
import io.github.llh4github.ksas.dbmodel.BaseModel
import io.github.llh4github.ksas.dbmodel.BaseRepository
import io.github.llh4github.ksas.exception.DbOperateException
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.mutation.KSimpleSaveResult
import org.babyfish.jimmer.sql.kt.ast.query.KConfigurableRootQuery
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

abstract class BaseServiceImpl<E : BaseModel>(
    override val entityType: KClass<E>,
    private val sqlClient: KSqlClient
) : BaseRepository<E>(sqlClient), BaseService<E> {

    @Transactional
    override fun updateById(entity: E): E {
        val rs = update(entity)
        testUpdateDbResult(rs)
        return rs.modifiedEntity
    }

    @Transactional
    override fun save(entity: E): E {
        val rs = insert(entity)
        testAddDbResult(rs)
        return rs.modifiedEntity
    }

    override fun getById(id: Long): E? {
        return findById(id)
    }

    /**
     * 检查新增数据结果. 如果新增数据失败, 则抛出异常
     * @throws DbOperateException 新增数据失败
     */
    protected fun testAddDbResult(
        rs: KSimpleSaveResult<*>,
        message: String = "新增数据失败"
    ) {
        if (!rs.isModified) {
            throw DbOperateException.addFailed(message = message)
        }
    }

    /**
     * 检查更新数据结果. 如果更新数据失败, 则抛出异常
     * @throws DbOperateException 更新数据失败
     */
    protected fun testUpdateDbResult(
        rs: KSimpleSaveResult<*>,
        message: String = "更新数据失败"
    ) {
        if (!rs.isModified) {
            throw DbOperateException.updateFailed(message = message)
        }
    }

    /**
     * 检查删除数据结果. 如果删除数据失败, 则抛出异常
     * @throws DbOperateException 删除数据失败
     */
    protected fun testDeleteDbResult(
        rs: KSimpleSaveResult<*>,
        message: String = "删除数据失败"
    ) {
        if (!rs.isModified) {
            throw DbOperateException.deleteFailed(message = message)
        }
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
}
