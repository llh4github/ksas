package io.github.llh4github.ksas.service.auth.impl

import io.github.llh4github.ksas.dbmodel.auth.PageRouter
import io.github.llh4github.ksas.dbmodel.auth.dto.PageRouterTreeView
import io.github.llh4github.ksas.dbmodel.auth.id
import io.github.llh4github.ksas.dbmodel.auth.name
import io.github.llh4github.ksas.exception.DbCommonException
import io.github.llh4github.ksas.service.BaseServiceImpl
import io.github.llh4github.ksas.service.auth.PageRouterService
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.count
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Service

@Service
class PageRouterServiceImpl(
    private val sqlClient: KSqlClient
) : PageRouterService,
    BaseServiceImpl<PageRouter>(PageRouter::class, sqlClient) {

    override fun allRouterTree(): List<PageRouterTreeView> {
        return createQuery { select(table.fetch(PageRouterTreeView::class)) }.execute()
    }

    override fun checkUnique(entity: PageRouter) {
        createQuery {
            where(table.name eq entity.name)
            select(count(table.id))
        }.fetchOne().let {
            if (it > 0) {
                throw DbCommonException.dataExists("页面路由名称已存在", null, "name", entity.name)
            }
        }
    }

//    @Transactional

//    override fun addUnique(entity: Input<PageRouter>): PageRouter {
//        val model = entity.toEntity()
//        createQuery {
//            where(table.name eq model.name)
//            select(count(table.id))
//        }.fetchOne().let {
//            if (it > 0) {
//                throw DbCommonException.dataExists("页面路由名称已存在", null, "name", model.name)
//            }
//        }
//        val rs = sqlClient.insert(model)
//        testAddDbResult(rs)
//        return rs.modifiedEntity
//    }
//
//    @Transactional
//    override fun checkAndUpdateById(entity: Input<PageRouter>): PageRouter {
//        val model = entity.toEntity()
//        createQuery {
//            where(table.id ne model.id)
//            where(table.name eq model.name)
//            select(count(table.id))
//        }.fetchOne().let {
//            if (it > 0) {
//                throw DbCommonException.dataExists("页面路由名称已存在", null, "name", model.name)
//            }
//        }
//        val rs = sqlClient.update(model)
//        testUpdateDbResult(rs)
//        return rs.modifiedEntity
//    }
}
