package io.github.llh4github.ksas.service

import io.github.llh4github.ksas.dbmodel.BaseModel
import io.github.llh4github.ksas.exception.DbOperateException
import org.babyfish.jimmer.Input
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.mutation.KDeleteResult
import org.babyfish.jimmer.sql.kt.ast.mutation.KSimpleSaveResult

/**
 * 业务逻辑的通用操作。此处定义的接口应当在具体的业务实现类中继承。
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

    fun addUniqueData(entity: E, sqlClient: KSqlClient): E {
        checkUnique(entity)
        val rs = sqlClient.update(entity)
        testAddDbResult(rs)
        return rs.modifiedEntity
    }

    fun updateUniqueData(entity: E, sqlClient: KSqlClient): E {
        checkUnique(entity)
        val rs = sqlClient.update(entity)
        testUpdateDbResult(rs)
        return rs.modifiedEntity
    }

}

/**
 * 检查新增数据结果. 如果新增数据失败, 则抛出异常
 * @throws DbOperateException 新增数据失败
 */
fun testAddDbResult(
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
fun testUpdateDbResult(
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
fun testDeleteDbResult(
    rs: KDeleteResult,
    message: String = "没有数据被删除"
) {
    if (rs.totalAffectedRowCount == 0) {
        throw DbOperateException.deleteFailed(message = message)
    }
}
