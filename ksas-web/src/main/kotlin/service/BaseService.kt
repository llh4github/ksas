package io.github.llh4github.ksas.service

import io.github.llh4github.ksas.commons.PageQueryParam
import io.github.llh4github.ksas.commons.PageResult
import io.github.llh4github.ksas.dbmodel.BaseModel
import org.babyfish.jimmer.Input
import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.ast.query.specification.KSpecification
import kotlin.reflect.KClass

@Suppress("TooManyFunctions")
interface BaseService<E : BaseModel> {

    fun save(entity: E): E
    fun save(entity: Input<E>): E = save(entity.toEntity())

    fun updateById(entity: E): E

    fun updateById(entity: Input<E>): E = updateById(entity.toEntity())

    fun getById(id: Long): E?
    fun getByIds(ids: List<Long>): List<E>

    fun <S : View<E>> getById(staticType: KClass<S>, id: Long): S?
    fun <S : View<E>> getByIds(staticType: KClass<S>, ids: List<Long>): List<S>

    /**
     * 通用分页查询
     * @param staticType 分页查询结果的静态类型，需要符合jimmer相关要求的类
     * @param querySpec 查询条件。jimmer 的 QBE类
     * @param pageQueryParam 分页参数 详见 [PageQueryParam]
     * @param sortField 排序字段。默认按照更新时间倒序。格式为 "字段名 排序方式"，如 "updatedTime desc"。
     * 多个字段排序时用逗号分隔，如 "updatedTime desc, createdTime desc"。
     * 参考文档 [动态排序](https://babyfish-ct.github.io/jimmer-doc/zh/docs/query/dynamic-order)
     * @return 分页查询结果，包含分页信息和查询结果
     */
    fun <S : View<E>> pageQuery(
        staticType: KClass<S>,
        querySpec: KSpecification<E>,
        pageQueryParam: PageQueryParam,
        sortField: String = "updatedTime desc",
    ): PageResult<S>

    /**
     * 移除数据。真实删除或逻辑删除由具体配置决定。
     */
    fun removeByIds(ids: List<Long>): Boolean

    fun removeById(id: Long): Boolean = removeByIds(listOf(id))

}
