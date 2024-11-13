package io.github.llh4github.ksas.service.auth

import io.github.llh4github.ksas.dbmodel.auth.Role
import io.github.llh4github.ksas.dbmodel.auth.dto.RoleAddInput
import io.github.llh4github.ksas.dbmodel.auth.dto.RoleUpdateInput
import io.github.llh4github.ksas.service.BaseService
import io.github.llh4github.ksas.service.CommonOperate

interface RoleService : CommonOperate<Role>, BaseService<Role> {

    fun addUnique(input: RoleAddInput): Role
    fun checkAndUpdateById(input: RoleUpdateInput): Role
}
