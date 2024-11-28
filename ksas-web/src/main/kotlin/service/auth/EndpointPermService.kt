package io.github.llh4github.ksas.service.auth

import io.github.llh4github.ksas.dbmodel.auth.EndpointPerm
import io.github.llh4github.ksas.dbmodel.auth.dto.EndpointPermAddInput
import io.github.llh4github.ksas.dbmodel.auth.dto.EndpointPermUpdateInput
import io.github.llh4github.ksas.service.BaseService
import org.babyfish.jimmer.View
import kotlin.reflect.KClass

interface EndpointPermService : BaseService<EndpointPerm> {

    fun addUnique(input: EndpointPermAddInput): EndpointPerm

    fun updateUnique(input: EndpointPermUpdateInput): EndpointPerm

    /**
     * 查询所有数据。慎用。
     */
    fun <S : View<EndpointPerm>> allData(
        staticType: KClass<S>,
        sortField: String = "updatedTime desc"
    ): List<S>
}
