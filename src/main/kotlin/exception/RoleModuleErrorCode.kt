package io.github.llh4github.ksas.exception

import org.babyfish.jimmer.error.ErrorFamily
import org.babyfish.jimmer.error.ErrorField

@ErrorFamily
enum class RoleModuleErrorCode {
    @ErrorField(name = "roleCode", type = String::class)
    ROLE_CODE_EXIST,
}
