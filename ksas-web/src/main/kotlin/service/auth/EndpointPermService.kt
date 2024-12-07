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

    @Deprecated("use listQuery instead", replaceWith = ReplaceWith("listQuery"))
    fun <S : View<EndpointPerm>> allData(
        staticType: KClass<S>,
        sortField: String = "updatedTime desc"
    ): List<S>
}
