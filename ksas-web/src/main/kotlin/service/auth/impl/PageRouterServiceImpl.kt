package io.github.llh4github.ksas.service.auth.impl

import io.github.llh4github.ksas.bo.PageRouterTreeBo
import io.github.llh4github.ksas.dbmodel.auth.*
import io.github.llh4github.ksas.dbmodel.auth.dto.*
import io.github.llh4github.ksas.exception.DbCommonException
import io.github.llh4github.ksas.service.BaseServiceImpl
import io.github.llh4github.ksas.service.auth.PageRouterService
import io.github.llh4github.ksas.service.common.PermissionCheckService
import io.github.llh4github.ksas.service.testAddDbResult
import org.babyfish.jimmer.kt.isLoaded
import org.babyfish.jimmer.sql.ast.LikeMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PageRouterServiceImpl(
    private val sqlClient: KSqlClient,
    private val permissionCheckService: PermissionCheckService,
) : PageRouterService,
    BaseServiceImpl<PageRouter>(PageRouter::class, sqlClient) {

    override fun allRouterTree(permissions: List<String>): List<PageRouterTreeBo> {
        val list = createQuery {
            where(
                or(
                    table.parentId.isNull(),
                    table.permissions {
                        or(
                            code valueIn permissions,
                            code.like("*", LikeMode.END)
                        )
                    }
                )
            )
            select(table.fetch(PageRouterWithPermissionView::class))
        }.execute().map { PageRouterTreeBo.from(it) }
        val map = list.associateBy { it.id }
        for (item in list) {
            item.children += map.filter { it.value.parentId == item.id }
                .filter {
                    permissionCheckService.checkHasPermission(
                        permissions,
                        it.value.permissionCodes
                    )
                }
                .values.toList()
        }
        return list.filter { it.parentId == null }.filter { it.children.isNotEmpty() }
    }

    override fun checkUnique(entity: PageRouter) {
        createQuery {
            if (isLoaded(entity, PageRouter::id)) {
                where(table.id ne entity.id)
            }
            where(table.name eq entity.name)
            select(count(table.id))
        }.fetchOne().let {
            if (it > 0) {
                throw DbCommonException.dataExists("页面路由名称已存在", null, "name", entity.name)
            }
        }
    }

    @Transactional
    override fun addUnique(input: PageRouterAddInput): PageRouter {
        val entity = input.toEntity()
        return addUniqueData(entity, sqlClient)
    }

    override fun updateUnique(input: PageRouterUpdateInput): PageRouter {
        val entity = input.toEntity()
        return updateUniqueData(entity, sqlClient)
    }

    override fun cascader(): List<PageRouterCascaderView> {
        return createQuery {
            where(table.parentId.isNull())
            select(table.fetch(PageRouterCascaderView::class))
        }.execute()
    }

    @Transactional
    override fun updatePermission(input: PageRouterPermissionUpdateInput): Boolean {
        val rs = sqlClient.save(input)
        testAddDbResult(rs)
        return rs.isModified
    }
}
