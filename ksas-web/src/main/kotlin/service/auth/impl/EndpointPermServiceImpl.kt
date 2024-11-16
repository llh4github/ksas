package io.github.llh4github.ksas.service.auth.impl

import io.github.llh4github.ksas.dbmodel.auth.EndpointPerm
import io.github.llh4github.ksas.dbmodel.auth.dto.EndpointPermAddInput
import io.github.llh4github.ksas.dbmodel.auth.dto.EndpointPermUpdateInput
import io.github.llh4github.ksas.dbmodel.auth.id
import io.github.llh4github.ksas.dbmodel.auth.permCode
import io.github.llh4github.ksas.exception.DbCommonException
import io.github.llh4github.ksas.service.BaseServiceImpl
import io.github.llh4github.ksas.service.UniqueDataChecker
import io.github.llh4github.ksas.service.auth.EndpointPermService
import org.babyfish.jimmer.kt.isLoaded
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.count
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.ne
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EndpointPermServiceImpl(
    private val sqlClient: KSqlClient
) : EndpointPermService,
    UniqueDataChecker<EndpointPerm>,
    BaseServiceImpl<EndpointPerm>(EndpointPerm::class, sqlClient) {

    override fun checkUnique(entity: EndpointPerm) {
        createQuery {
            where(table.permCode eq entity.permCode)
            if (isLoaded(entity, EndpointPerm::id)) {
                where(table.id ne entity.id)
            }
            select(count(table.id))
        }.fetchOne().let { count ->
            if (count > 0) {
                throw DbCommonException.dataExists(
                    message = "权限编码已存在",
                    fieldName = "permCode",
                    fieldValue = entity.permCode,
                )
            }
        }
    }


    @Transactional
    override fun addUnique(input: EndpointPermAddInput): EndpointPerm {
        val entity = input.toEntity()
        return addUniqueData(entity, sqlClient)

    }

    @Transactional
    override fun updateUnique(input: EndpointPermUpdateInput): EndpointPerm {
        val entity = input.toEntity()
        return updateUniqueData(entity, sqlClient)
    }
}
